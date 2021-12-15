package com.aws.lambda.spring.controller;

import com.aws.lambda.spring.service.S3UploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private final S3UploaderService s3UploaderService;

    @PostMapping("/images")
    public String upload(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        System.out.println("multipartFile.getOriginalFilename() = " + multipartFile.getOriginalFilename());
        s3UploaderService.upload(multipartFile, "static");
        return "test";
    }
}
