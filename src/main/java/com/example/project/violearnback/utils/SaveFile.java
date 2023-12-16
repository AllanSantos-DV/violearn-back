package com.example.project.violearnback.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class SaveFile {

    public static String saveFileInput(MultipartFile multipartFile, String pathFile) throws IOException {
        if (multipartFile == null || multipartFile.getOriginalFilename() == null) {
            return "";
        }
        String path = System.getProperty("user.dir") + pathFile;
        File directory = new File(path);
        if (!directory.exists()) directory.mkdirs();
        path += multipartFile.getOriginalFilename();
        File file = new File(path);
        if (file.exists()) {
            path = path.replace(multipartFile.getOriginalFilename(), System.currentTimeMillis() + multipartFile.getOriginalFilename());
            file = new File(path);
        }
        multipartFile.transferTo(file);
        return path;
    }

}
