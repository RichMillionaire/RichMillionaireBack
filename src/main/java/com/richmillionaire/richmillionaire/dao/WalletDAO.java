package com.richmillionaire.richmillionaire.dao;

import java.util.List;
import java.util.UUID;

import com.richmillionaire.richmillionaire.models.User;
import com.richmillionaire.richmillionaire.models.Wallet;

public interface WalletDAO {
    Wallet save(Wallet wallet) throws Exception;

    List<Wallet> findAll() throws Exception;

    Wallet findById(String publicKey) throws Exception;

    void deleteById(String publicKey) throws Exception;

    List<Wallet> findByUser(User user) throws Exception;
    
    List<Wallet> findByUserId(UUID userId) throws Exception;
}