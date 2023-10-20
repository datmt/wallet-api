package com.datmt.wallet.api.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.io.Serializable;

@Document(collection = "files")
@Data
public class FileUpload implements Serializable {
    @MongoId
    private String id;
    private String s3Path;
    private String ownerId;
}
