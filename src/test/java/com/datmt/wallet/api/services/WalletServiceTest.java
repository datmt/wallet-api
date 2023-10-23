package com.datmt.wallet.api.services;

import com.datmt.wallet.api.models.Wallet;
import com.datmt.wallet.api.repositories.TransactionRepository;
import com.datmt.wallet.api.repositories.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {WalletService.class})
class WalletServiceTest {

    @MockBean
    private WalletRepository walletRepository;
    @MockBean
    private CurrentUserService currentUserService;
    @MockBean
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletService walletService;

    @BeforeEach
    void setUp() {
        when(currentUserService.getCurrentUserId()).thenReturn("1");
        var wallet = new Wallet();
        wallet.setId("1");
        wallet.setTitle("My Wallet");
        wallet.setCurrency("USD");
        wallet.setOwnerId("1");
        when(walletRepository.findByOwnerIdAndId("1", "1")).thenReturn(java.util.Optional.of(wallet));
        when(walletRepository.findByOwnerIdAndId("2", "1")).thenReturn(java.util.Optional.empty());

        when(walletRepository.save(any())).thenReturn(wallet);
    }

    @Test
    @DisplayName("Create wallet with empty title")
    void create() {
        var wallet = new Wallet();
        wallet.setTitle("");
        wallet.setCurrency("USD");

        assertThrows(IllegalArgumentException.class, () -> walletService.create(wallet));
    }

    @Test
    @DisplayName("Create a valid wallet return with valid owner id")
    void createValidWallet() {
        var wallet = new Wallet();
        wallet.setTitle("First wallet");
        wallet.setCurrency("USD");

        var savedWallet = walletService.create(wallet);
        assertEquals("1", savedWallet.getOwnerId());
    }

    @Test
    @DisplayName("Update wallet with empty title must throw exception")
    void update() {
        var wallet = new Wallet();
        wallet.setId("1");
        wallet.setTitle("");
        wallet.setCurrency("USD");

        assertThrows(IllegalArgumentException.class, () -> walletService.update(wallet));
    }

    @Test
    @DisplayName("Update wallet with valid title but wrong owner must throw exception")
    void updateOwner() {
        var wallet = new Wallet();
        wallet.setId("1");
        wallet.setTitle("My Wallet");
        wallet.setCurrency("USD");
        wallet.setOwnerId("2");

        assertThrows(IllegalArgumentException.class, () -> walletService.update(wallet));
    }

    @Test
    void get() {
    }

    @Test
    void list() {
    }

    @Test
    void delete() {
    }
}