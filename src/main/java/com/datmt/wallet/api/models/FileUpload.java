package com.datmt.wallet.api.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@Document(collection = "files")
public class FileUpload implements Serializable {
    @Id
    private String id;
    private String s3Path;
    private String ownerId;
}
