package com.datmt.wallet.api.controllers;

import com.datmt.wallet.api.models.PageResponse;
import com.datmt.wallet.api.models.Wallet;
import com.datmt.wallet.api.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/{id}")
    public Wallet get(@PathVariable String id) {
        return walletService.get(id);
    }

    @GetMapping
    public PageResponse<Wallet> list(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return walletService.list(page, size);
    }

    @PostMapping
    public Wallet create(@RequestBody Wallet wallet) {
        return walletService.create(wallet);
    }

    @PutMapping
    public Wallet update(@RequestBody Wallet wallet) {
        return walletService.update(wallet);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        walletService.delete(id);
    }

}
