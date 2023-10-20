package com.datmt.wallet.api.repositories;

import com.datmt.wallet.api.models.Transaction;
import com.datmt.wallet.api.models.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    Page<Transaction> findAllByWalletId(String walletId, Pageable page);

    Page<Transaction> findAllByWalletIdAndType(String walletId, TransactionType type, Pageable page);

    void deleteAllByWalletId(String walletId);
}
