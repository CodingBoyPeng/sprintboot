package com.pf.springboot.controller;

import com.pf.springboot.service.IDocumentService;
import com.pf.springboot.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: PengFeng
 * @Description:
 * @Date: Created in 17:14 2021/8/8
 */
@RestController
@RequestMapping(value = "document")
public class DocumentController {

    @Autowired
    IDocumentService documentService;

    @ResponseBody
    @GetMapping("download")
    public void downloadZip(HttpServletResponse response) {
        documentService.downloadZipDocument(response);
    }

    @ResponseBody
    @GetMapping("upload")
    public AjaxResult upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return AjaxResult.error("文件列表不能为空");
        }

        documentService.upload(file);
        return AjaxResult.success();
    }
}
