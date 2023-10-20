package com.datmt.wallet.api.services;

import com.datmt.wallet.api.models.PageResponse;
import com.datmt.wallet.api.models.Wallet;
import com.datmt.wallet.api.repositories.TransactionRepository;
import com.datmt.wallet.api.repositories.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final CurrentUserService currentUserService;
    private final TransactionRepository transactionRepository;

    public Wallet create(Wallet wallet) {
        //assert that wallet has a title
        if (wallet.getTitle() == null || wallet.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Wallet title is required");
        }

        //set default currency if not provided
        if (wallet.getCurrency() == null || wallet.getCurrency().isEmpty()) {
            wallet.setCurrency("USD");
        }
        wallet.setOwnerId(currentUserService.getCurrentUserId());

        return walletRepository.save(wallet);
    }

    public Wallet update(Wallet wallet) {
        //assert that wallet has a title
        if (wallet.getTitle() == null || wallet.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Wallet title is required");
        }

        walletRepository.findByOwnerIdAndId(currentUserService.getCurrentUserId(), wallet.getId())
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));

        return walletRepository.save(wallet);
    }

    public Wallet get(String walletId) {
        return walletRepository.findByOwnerIdAndId(currentUserService.getCurrentUserId(), walletId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));
    }

    public PageResponse<Wallet> list(int page, int size) {
        var result = walletRepository.findAllByOwnerId(currentUserService.getCurrentUserId(), PageRequest.of(page, size));

        return PageResponse.<Wallet>builder()
                .data(result.getContent())
                .page(result.getNumber())
                .size(result.getSize())
                .totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .build();
    }

    public void delete(String walletId) {
        walletRepository.findByOwnerIdAndId(currentUserService.getCurrentUserId(), walletId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));

        //delete all tx
        transactionRepository.deleteAllByWalletId(walletId);
        walletRepository.deleteById(walletId);
    }

}
