package com.datmt.wallet.api.repositories;

import com.datmt.wallet.api.models.FileUpload;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadRepository extends MongoRepository<FileUpload, String> {
}
