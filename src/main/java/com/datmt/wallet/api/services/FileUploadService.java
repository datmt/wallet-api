package com.datmt.wallet.api.services;

import com.datmt.wallet.api.helpers.AwsS3File;
import com.datmt.wallet.api.models.FileUpload;
import com.datmt.wallet.api.repositories.FileUploadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final FileUploadRepository fileUploadRepository;
    private final CurrentUserService currentUserService;

    public File multipartToFile(MultipartFile multipartFile) throws IOException {
        File file = File.createTempFile("temp", null);
        multipartFile.transferTo(file);
        return file;
    }

    public FileUpload upload(MultipartFile multipartFile) throws IOException {
        var file = multipartToFile(multipartFile);

        var storageFilePath = UUID.randomUUID() + multipartFile.getOriginalFilename();

        var awsS3File = new AwsS3File();
        var result = awsS3File.uploadObjectPrivate(file, storageFilePath);

        if (result.getContentMd5() == null)
            throw new IOException("Upload failed");

        var fileUpload = new FileUpload();
        fileUpload.setOwnerId(currentUserService.getCurrentUserId());
        fileUpload.setS3Path(storageFilePath);

        return fileUploadRepository.save(fileUpload);
    }
}
