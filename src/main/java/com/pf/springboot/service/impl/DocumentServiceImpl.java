package com.pf.springboot.service.impl;

import com.pf.springboot.entity.Document;
import com.pf.springboot.service.IDocumentService;
import com.pf.springboot.util.ucloud.UCloudFileUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.zip.ZipOutputStream;

/**
 * @Author: PengFeng
 * @Description:
 * @Date: Created in 21:05 2021/8/8
 */
@Service
public class DocumentServiceImpl implements IDocumentService {
    @Resource(name = "threadPool")
    ThreadPoolTaskExecutor executor;

    private static final CountDownLatch uploadLatch = new CountDownLatch(5);

    @Override
    public void downloadZipDocument(HttpServletResponse response) {
        List<Boolean> threadResults = new ArrayList<>();
        setZipResponse(response);
        List<Document> documents = getDocuments();
        final CountDownLatch latch = new CountDownLatch(documents.size());

        try {

            OutputStream outputStream = response.getOutputStream();
            ZipOutputStream stream = new ZipOutputStream(outputStream);
            for (Document document : documents) {
                Future<Boolean> submit = executor.submit(() -> {
                    try {
                        UCloudFileUtils.getStream(stream, document.getUrl(), document.getKeyName());
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                });
                threadResults.add(submit.get());
            }

            for (Boolean threadResult : threadResults) {
                System.out.println(threadResult);
                if (threadResult) {
                    latch.countDown();
                }
            }

            outputStream.close();
            stream.close();
            latch.await();
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void upload(MultipartFile file) {
        System.out.println(file.getContentType());
        System.out.println(file.getSize());
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());

    }

    private List<Document> getDocuments() {
        Document file1 = new Document();
        file1.setUrl("http://suanzong.cn-gd.ufileos.com/dev/doc/20210720/审核文件20210720185610.txt?UCloudPublicKey=TOKEN_31bdad47-cf77-420a-b7a1-25cbcbacd590&Signature=IAVhWMRlvPmBvsgqpEF87/6igLs=");
        file1.setKeyName("审核文件20210720185610.txt");
        Document file2 = new Document();
        file2.setUrl("http://suanzong.cn-gd.ufileos.com/dev/doc/20210723/%E6%95%B0%E6%8D%AE%E8%BF%81%E7%A7%BB20210723145642.txt?UCloudPublicKey=TOKEN_31bdad47-cf77-420a-b7a1-25cbcbacd590&Signature=kTNGpbYblHlheM+tN3EYfGfzoNY=");
        file2.setKeyName("数据迁移20210723145642.txt");
        Document file3 = new Document();
        file3.setUrl("http://suanzong.cn-gd.ufileos.com/dev/doc/20210723/%E6%95%B0%E6%8D%AE%E8%BF%81%E7%A7%BB20210723145642.txt?UCloudPublicKey=TOKEN_31bdad47-cf77-420a-b7a1-25cbcbacd590&Signature=kTNGpbYblHlheM+tN3EYfGfzoNY=");
        file3.setKeyName("数据迁移20210723143333.txt");
        Document file4 = new Document();
        file4.setUrl("http://suanzong.cn-gd.ufileos.com/dev/doc/20210723/%E6%95%B0%E6%8D%AE%E8%BF%81%E7%A7%BB20210723145642.txt?UCloudPublicKey=TOKEN_31bdad47-cf77-420a-b7a1-25cbcbacd590&Signature=kTNGpbYblHlheM+tN3EYfGfzoNY=");
        file4.setKeyName("数据迁移20210723144444.txt");

        return new ArrayList<Document>(){{
            this.add(file1);
            this.add(file2);
            this.add(file3);
            this.add(file4);
        }};
    }

    private void setZipResponse(HttpServletResponse response) {
        // 清除首部的空白行
        response.reset();
        // 设置Response容器的编码
        response.setCharacterEncoding("UTF-8");
        // 不同类型的文件对应不同的MIME类型
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition","attachment; filename=文件.zip");
    }


}
