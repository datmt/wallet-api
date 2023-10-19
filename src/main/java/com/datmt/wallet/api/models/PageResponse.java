package com.datmt.wallet.api.models;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
public class PageResponse<T> implements Serializable {
    private List<T> data;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
}
