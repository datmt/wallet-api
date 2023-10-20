package com.datmt.wallet.api.controllers;

import com.datmt.wallet.api.models.PageResponse;
import com.datmt.wallet.api.models.Transaction;
import com.datmt.wallet.api.services.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
class TransactionControllerTest {
    @MockBean
    TransactionService transactionService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        var brandNewTransaction = new Transaction();
        brandNewTransaction.setAmount(1000);
        brandNewTransaction.setImages(List.of("image1", "image2"));
        brandNewTransaction.setOwnerId("test-owner-id");
        brandNewTransaction.setCurrency("USD");
        brandNewTransaction.setId("test-id-created");

        var existingTransaction = new Transaction();
        existingTransaction.setAmount(1000);
        existingTransaction.setImages(List.of("image1", "image2"));
        existingTransaction.setOwnerId("test-owner-id");
        existingTransaction.setCurrency("USD");
        existingTransaction.setId("test-id-updated");

        var listTx = PageResponse.<Transaction>builder()
                .page(0)
                .size(10)
                .data(List.of(existingTransaction))
                .build();

        when(transactionService.update(any())).thenReturn(existingTransaction);
        when(transactionService.create(any())).thenReturn(brandNewTransaction);
        when(transactionService.list(anyString(), anyInt(), anyInt())).thenReturn(listTx);
    }

    @Test
    @DisplayName("Test create transaction")
    void create() throws Exception {
        var transaction = new Transaction();

        transaction.setAmount(1000);

        transaction.setImages(List.of("image1", "image2"));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(transaction);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(1000))
                .andExpect(jsonPath("$.ownerId").value("test-owner-id"))
                .andExpect(jsonPath("$.currency").value("USD"));
    }

    @Test
    @DisplayName("Test update transaction")
    void update() throws Exception {
        var transaction = new Transaction();

        transaction.setAmount(1000);

        transaction.setImages(List.of("image1", "image2"));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(transaction);

        mockMvc.perform(put("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(1000))
                .andExpect(jsonPath("$.ownerId").value("test-owner-id"))
                .andExpect(jsonPath("$.currency").value("USD"));
    }

    @Test
    @DisplayName("Test list transactions")
    void list() throws Exception {
        mockMvc.perform(get("/api/transactions/wallet/test-wallet-id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(10));
    }
}