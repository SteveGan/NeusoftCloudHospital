package com.neuedu.hospitalbackend.controller;

import com.neuedu.hospitalbackend.component.FastDFSFile;
import com.neuedu.hospitalbackend.component.FileManager;
import org.csource.common.NameValuePair;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author: Raven
 * @Date: 2019/6/30 3:56 AM
 */
@RestController
@RequestMapping("/dfs")
@CrossOrigin
public class ImageUploadController {
    @RequestMapping("/upload")
    public String upload(MultipartFile photo) throws IOException {
        // 获取文件后缀名
        String ext = "jpg";
        System.out.println(photo.getOriginalFilename());
        FileInputStream fileReader = (FileInputStream) photo.getInputStream();

        byte[] bytes = new byte[10000000];
        int length = fileReader.read(bytes);
        FastDFSFile file = new FastDFSFile(bytes, ext);

        NameValuePair[] metaList = new NameValuePair[4];
        metaList[0] = new NameValuePair("fileName", "abc");
        metaList[1] = new NameValuePair("fileLength", String.valueOf(length));
        metaList[2] = new NameValuePair("fileExt", ext);
        metaList[3] = new NameValuePair("fileAuthor", "Raven");
        String filePath = FileManager.upload(file, metaList);
        System.out.println(filePath);

        return filePath;
    }
}
