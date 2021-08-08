package com.pf.springboot.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: PengFeng
 * @Description:
 * @Date: Created in 21:04 2021/8/8
 */
public interface IDocumentService {
    void downloadZipDocument(HttpServletResponse response);

    void upload(MultipartFile file);
}
