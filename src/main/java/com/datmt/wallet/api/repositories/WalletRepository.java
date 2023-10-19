package com.datmt.wallet.api.repositories;

import com.datmt.wallet.api.models.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends MongoRepository<Wallet, String> {
    Optional<Wallet> findByOwnerIdAndId(String ownerId, String walletId);

    Page<Wallet> findAllByOwnerId(String ownerId, Pageable page);
}
