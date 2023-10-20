package com.datmt.wallet.api.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document(collection = "wallets")
@Data
public class Wallet implements Serializable {

    @Id
    private String id;
    private String ownerId;
    private String title;
    private String description;
    private long balance;
    private String currency;

    @CreatedDate
    private LocalDateTime createdDate;
}
