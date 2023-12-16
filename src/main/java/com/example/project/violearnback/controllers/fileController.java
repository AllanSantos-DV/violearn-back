package com.example.project.violearnback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/file")
public class fileController {

    private static final String IMAGE_PATH = "./dados/imagens/";
    private static final String FILE_PATH = "./dados/file/";

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping(IMAGE_PATH + "/{path}/{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> getImage(@PathVariable String path , @PathVariable String fileName) {
        try {
            Resource resource = resourceLoader.getResource("classpath:" + IMAGE_PATH + path + "/" + fileName);
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok().body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(FILE_PATH + "/{path}/{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String path , @PathVariable String fileName) {
        try {
            Resource resource = resourceLoader.getResource("classpath:" + FILE_PATH + path + "/" + fileName);
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok().body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
