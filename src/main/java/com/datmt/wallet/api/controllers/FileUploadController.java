package com.datmt.wallet.api.controllers;

import com.datmt.wallet.api.models.FileUpload;
import com.datmt.wallet.api.services.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileUploadController {
    private final FileUploadService fileUploadService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FileUpload upload(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return fileUploadService.upload(file);
    }
}
