package com.datmt.wallet.api.controllers;

import com.datmt.wallet.api.models.Wallet;
import com.datmt.wallet.api.services.WalletService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WalletController.class)
@AutoConfigureMockMvc(addFilters = false)
class WalletControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    WalletService walletService;

    @Test
    @DisplayName("Test get wallet by id")
    void getWallet() throws Exception {
        var wallet = new Wallet();
        wallet.setId("test-id");
        wallet.setBalance(1000);
        wallet.setOwnerId("test-owner-id");
        wallet.setTitle("test-title");

        when(walletService.get("test-id")).thenReturn(wallet);

        mockMvc.perform(get("/api/wallets/test-id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("test-id"))
                .andExpect(jsonPath("$.balance").value(1000))
                .andExpect(jsonPath("$.ownerId").value("test-owner-id"))
                .andExpect(jsonPath("$.title").value("test-title"));

    }

    @Test
    void list() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}