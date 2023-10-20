package com.datmt.wallet.api.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "transactions")
@Data
public class Transaction implements Serializable {

    @Id
    private String id;
    private String walletId;
    private String title;
    private String description;
    private String category;
    private TransactionType type;
    private List<String> images = List.of();//object ids of images
    private long amount;
    private String currency;
    private String ownerId;
    private LocalDateTime createdDate;
}
