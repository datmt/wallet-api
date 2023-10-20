package com.datmt.wallet.api.controllers;

import com.datmt.wallet.api.models.PageResponse;
import com.datmt.wallet.api.models.Transaction;
import com.datmt.wallet.api.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction create(@RequestBody Transaction transaction) {
        return transactionService.create(transaction);
    }

    @PutMapping
    public Transaction update(@RequestBody Transaction transaction) {
        return transactionService.update(transaction);
    }

    @GetMapping("/wallet/{walletId}")
    public PageResponse<Transaction> list(
            @PathVariable String walletId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return transactionService.list(walletId, page, size);
    }
}
