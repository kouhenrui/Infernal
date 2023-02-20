package com.example.infernal.controller;

import com.example.infernal.util.R.RCode;
import com.example.infernal.util.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.example.infernal.util.file.FileTypeUtils.getFileTypeBySuffix;

/**
 * @auther khr
 * @date 2022/8/23 13:50
 */

@Slf4j
@RestController
@RequestMapping("/commen")
public class CommenController {
    @Value("${uploadphoto}")
    private String photopath;
    @Value("${uploadfile}")
    private String filepath;

    /**
     * 1.文件保存在服务器，url地址保存在数据库
     * 上传成功之后返回成功保存的url地址
     */
    @PostMapping("/upload")
    public @ResponseBody Object upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        if (!file.isEmpty()) {
            String OriginalFilename = file.getOriginalFilename();//获取原文件名
            String suffixName = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));//获取文件后缀名
            String type = getFileTypeBySuffix(OriginalFilename);
            System.out.println(type);
            if (type.equals("gif") || type.equals("png") || type.equals("jpg")) {
                File uploadDir = new File(photopath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String filename = UUID.randomUUID().toString() + suffixName;
                file.transferTo(new File(uploadDir, filename));
                return filename;
            } else {
                File uploadDir = new File(filepath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String filename = UUID.randomUUID().toString() + suffixName;
                file.transferTo(new File(uploadDir, filename));
                return filename;
            }

        } else {
            System.out.println("文件为空");
            return RCode.UPLOAD_TYPE_FILED;
        }
    }

}
