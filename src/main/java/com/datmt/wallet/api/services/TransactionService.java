package com.datmt.wallet.api.services;

import com.datmt.wallet.api.models.PageResponse;
import com.datmt.wallet.api.models.Transaction;
import com.datmt.wallet.api.repositories.TransactionRepository;
import com.datmt.wallet.api.repositories.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CurrentUserService currentUserService;
    private final WalletRepository walletRepository;

    public Transaction create(Transaction transaction) {

        validateTransaction(transaction);
        transaction.setOwnerId(currentUserService.getCurrentUserId());
        return transactionRepository.save(transaction);
    }

    public Transaction update(Transaction transaction) {
        //make sure wallet exists
        validateTransaction(transaction);
        return transactionRepository.save(transaction);
    }

    public PageResponse<Transaction> list(String walletId, int page, int size) {

        walletRepository.findByOwnerIdAndId(currentUserService.getCurrentUserId(), walletId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));


        var result = transactionRepository.findAllByWalletId(walletId, PageRequest.of(page, size));

        return PageResponse.<Transaction>builder()
                .data(result.getContent())
                .page(result.getNumber())
                .size(result.getSize())
                .totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .build();
    }

    private void validateTransaction(Transaction transaction) {
        walletRepository.findByOwnerIdAndId(currentUserService.getCurrentUserId(), transaction.getWalletId()).orElseThrow(() -> new IllegalArgumentException("Wallet not found"));

        if (transaction.getType() == null) {
            throw new IllegalArgumentException("Transaction type is required");
        }

        if (transaction.getTitle() == null) {
            throw new IllegalArgumentException("Transaction title is required");
        }

    }

    public void delete(String id) {
        //find the tx
        var transaction = transactionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        log.info("find wallet id: {} by owner id {}", transaction.getWalletId(), currentUserService.getCurrentUserId());
        //verify that the owner owns the wallet
        walletRepository.findByOwnerIdAndId(currentUserService.getCurrentUserId(), transaction.getWalletId())
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));

        //delete the tx
        transactionRepository.deleteById(id);
    }
}
