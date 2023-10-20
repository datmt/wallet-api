package com.datmt.wallet.api.controllers;

import com.datmt.wallet.api.models.FileUpload;
import com.datmt.wallet.api.services.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileUploadController {
    private final FileUploadService fileUploadService;

    @PostMapping
    public FileUpload upload(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return fileUploadService.upload(file);
    }
}
