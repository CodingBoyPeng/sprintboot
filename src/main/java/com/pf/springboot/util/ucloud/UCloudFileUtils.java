package com.pf.springboot.util.ucloud;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.multi.MultiUploadInfo;
import cn.ucloud.ufile.api.object.multi.MultiUploadPartState;
import cn.ucloud.ufile.api.object.policy.PolicyParam;
import cn.ucloud.ufile.api.object.policy.PutPolicy;
import cn.ucloud.ufile.api.object.policy.PutPolicyForCallback;
import cn.ucloud.ufile.bean.DownloadStreamBean;
import cn.ucloud.ufile.bean.MultiUploadResponse;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.bean.base.BaseObjectResponseBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.ProgressConfig;
import cn.ucloud.ufile.util.FileUtil;
import cn.ucloud.ufile.util.JLog;
import cn.ucloud.ufile.util.MimeTypeUtil;
import cn.ucloud.ufile.util.StorageType;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author: PengFeng
 * @Description:
 * @Date: Created in 23:52 2021/8/6
 */
public class UCloudFileUtils {
    @Value("ucloud.bucketName")
    private static String bucketName;


    /**
     * 单文件上传
     * @param file
     * @param mimeType
     * @param keyName
     */
    public static void putFile(File file, String mimeType, String keyName) {
        try {
            /**
             * 上传回调策略
             * 必须填写回调接口url(目前仅支持http，不支持https)，可选填回调参数，回调参数请自行决定是否需要urlencode
             * 若配置上传回调，则上传接口的回调将会透传回调接口的response，包括httpCode
             */
            PutPolicy putPolicy = new PutPolicyForCallback.Builder("http://xxx.xxx.xxx.xxx[:port][/path]")
                    .addCallbackBody(new PolicyParam("key", "value"))
                    .build();
            PutObjectResultBean response = UfileClient.object(AuthConstants.OBJECT_AUTHORIZER, AuthConstants.CONFIG)
                    .putObject(file, mimeType)
                    .nameAs(keyName)
                    .toBucket(bucketName)
                    /**
                     * 配置文件存储类型，分别是标准、低频、冷存，对应有效值：STANDARD | IA | ARCHIVE
                     */
                    .withStorageType(StorageType.STANDARD)
                    /**
                     * 配置进度监听
                     */
                    .setOnProgressListener((bytesWritten, contentLength) -> JLog.D(UCloudTag.UPLOAD_TAG, String.format("[progress] = %d%% - [%d/%d]", (int) (bytesWritten * 1.f / contentLength * 100), bytesWritten, contentLength)))
                    .execute();
            JLog.D(UCloudTag.UPLOAD_TAG, String.format("[res] = %s", (response == null ? "null" : response.toString())));
        } catch (UfileClientException | UfileServerException e) {
            e.printStackTrace();
        }
    }


    /**
     * 分片文件上传
     * @param file
     */
    public static void putMultiFile(File file) {
        MultiUploadInfo state = initMultiUpload(file, file.getName());
        if (state == null) {
            return;
        }

        JLog.D(UCloudTag.MULTI_UPLOAD_TAG, String.format("[init state] = %s", state.toString()));

        List<MultiUploadPartState> partStates = multiUpload(file, state);
        // 若上传分片结果列表为空，则失败，需中断上传操作。否则完成上传
        if (partStates == null || partStates.isEmpty())
            abortMultiUpload(state);
        else
            finishMultiUpload(state, partStates);
    }

    private static MultiUploadInfo initMultiUpload(File file, String keyName) {
        try {
            // MimeTypeUtil可能支持的type类型不全，用户可以按需自行填写
            String mimeType = MimeTypeUtil.getMimeType(file);
            return UfileClient.object(AuthConstants.OBJECT_AUTHORIZER, AuthConstants.CONFIG)
                    .initMultiUpload(keyName, mimeType, bucketName)
                    .withStorageType(StorageType.STANDARD)
                    .execute();
        } catch (UfileClientException | UfileServerException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static List<MultiUploadPartState> multiUpload(File file, MultiUploadInfo state) {
        List<MultiUploadPartState> partStates = null;
        byte[] buffer = new byte[state.getBlkSize()];
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            int len = 0;
            int count = 0;

            partStates = new ArrayList<>();
            // 将数据根据state中指定的大小进行分片
            while ((len = is.read(buffer)) > 0) {
                final int index = count++;
                byte[] sendData = Arrays.copyOf(buffer, len);
                int uploadCount = 0;

                // 可支持重试3次上传
                while (uploadCount < 3) {
                    try {
                        MultiUploadPartState partState = UfileClient.object(AuthConstants.OBJECT_AUTHORIZER, AuthConstants.CONFIG)
                                .multiUploadPart(state, sendData, index)
                                .withProgressConfig(ProgressConfig.callbackWithPercent(50))

                                .setOnProgressListener((bytesWritten, contentLength) -> JLog.D(UCloudTag.UPLOAD_TAG, String.format("[index] = %d\t[progress] = %d%% - [%d/%d]", index,
                                        (int) (bytesWritten * 1.f / contentLength * 100), bytesWritten, contentLength)))
                                .execute();
                        if (partState == null) {
                            uploadCount++;
                            continue;
                        }

                        partStates.add(partState);
                        break;
                    } catch (UfileClientException | UfileServerException e) {
                        e.printStackTrace();
                        // 尝试次数+1
                        uploadCount++;
                    }
                }

                if (uploadCount == 3)
                    return null;
            }

            
            return partStates;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileUtil.close(is);
        }

        return null;
    }

    private static void abortMultiUpload(MultiUploadInfo info) {
        try {
            BaseObjectResponseBean abortRes = UfileClient.object(AuthConstants.OBJECT_AUTHORIZER, AuthConstants.CONFIG)
                    .abortMultiUpload(info)
                    .execute();
            JLog.D(UCloudTag.MULTI_UPLOAD_TAG, "abort->" + abortRes.toString());
        } catch (UfileClientException | UfileServerException e) {
            e.printStackTrace();
        }
    }

    private static MultiUploadResponse finishMultiUpload(MultiUploadInfo state, List<MultiUploadPartState> partStates) {
        try {
            /**
             * 上传回调策略
             * 必须填写回调接口url(目前仅支持http，不支持https)，可选填回调参数，回调参数请自行决定是否需要urlencode。
             * 若配置上传回调，则上传接口的回调将会透传回调接口的response，包括httpCode
             */
            PutPolicy putPolicy = new PutPolicyForCallback.Builder("http://xxx.xxx.xxx.xxx[:port][/path]")
                    .addCallbackBody(new PolicyParam("key", "value"))
                    .build();
            MultiUploadResponse res = UfileClient.object(AuthConstants.OBJECT_AUTHORIZER, AuthConstants.CONFIG)
                    .finishMultiUpload(state, partStates)
                    .execute();

            JLog.D(UCloudTag.MULTI_UPLOAD_TAG, "finish->" + res.toString());

            return res;
        } catch (UfileClientException | UfileServerException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 下载压缩文件
     * @param zops
     * @param url
     * @param keyName
     */
    public static boolean getStream(ZipOutputStream zops, String url, String keyName) {
        try {
            zops.putNextEntry(new ZipEntry(keyName));
            DownloadStreamBean response = UfileClient.object(AuthConstants.OBJECT_AUTHORIZER, AuthConstants.CONFIG)
                    .getStream(url)
                    .setOnProgressListener((bytesWritten, contentLength) -> JLog.D(UCloudTag.DOWNLOAD_TAG, String.format("[progress] = %d%% - [%d/%d]", (int) (bytesWritten * 1.f / contentLength * 100), bytesWritten, contentLength)))
                    .execute();

            InputStream inputStream = response.getInputStream();
            byte[] tempbytes = new byte[100];
            int byteread;
            while ((byteread = inputStream.read(tempbytes)) != -1) {
                zops.write(tempbytes, 0, byteread);
            }
            zops.closeEntry();
            zops.flush();
            JLog.D(UCloudTag.DOWNLOAD_TAG, String.format("[res] = %s", response.toString()));
            return true;
        } catch (UfileServerException | UfileClientException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
