package com.pf.springboot.util.ucloud;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.BucketAuthorization;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileBucketLocalAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.http.HttpClient;
import cn.ucloud.ufile.util.JLog;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: PengFeng
 * @Description:
 * @Date: Created in 23:54 2021/8/6
 */
public class AuthConstants {

    @Value("ucloud.publicKey")
    private static String publicKey;
    @Value("ucloud.privateKey")
    private static String privateKey;
    static {
        /**
         * 开启Debug级别日志
         */
        JLog.SHOW_TEST = true;
        JLog.SHOW_DEBUG = true;

        /**
         * 配置UfileClient，必须在使用UfileClient之前调用
         */
        UfileClient.configure(new UfileClient.Config(
                new HttpClient.Config(10, 5, TimeUnit.MINUTES)
                        .setTimeout(10 * 1000, 30 * 1000, 30 * 1000)
                        .setExecutorService(Executors.newSingleThreadExecutor())));
    }

    /**
     * 本地Bucket相关API的签名器（账号在ucloud 的API 公私钥，不能使用token）
     * 如果只用到了文件操作，不需要配置下面的bucket 操作公私钥
     */
    public static final BucketAuthorization BUCKET_AUTHORIZER = new UfileBucketLocalAuthorization(
            publicKey, privateKey);

    /**
     * 本地Object相关API的签名器
     * 请修改下面的公私钥
     */
    public static final ObjectAuthorization OBJECT_AUTHORIZER = new UfileObjectLocalAuthorization(
            publicKey, privateKey);

    /**
     * 远程Object相关API的签名器
     */
//    public static final ObjectAuthorization OBJECT_AUTHORIZER = new UfileObjectRemoteAuthorization(
//            您的公钥,
//            new ObjectRemoteAuthorization.ApiConfig(
//                    "http://your_domain/applyAuth",
//                    "http://your_domain/applyPrivateUrlAuth"
//            ));

    public static ObjectConfig CONFIG = new ObjectConfig("cn-gd", "ufileos.com");

}
