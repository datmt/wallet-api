package com.datmt.wallet.api.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "files")
@Data
public class FileUpload implements Serializable {
    @Id
    private String id;
    private String s3Path;
    private String ownerId;
}
