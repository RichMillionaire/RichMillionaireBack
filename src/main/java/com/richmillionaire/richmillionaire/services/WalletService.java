package com.richmillionaire.richmillionaire.services;

import java.util.List;
import java.util.UUID;

import com.richmillionaire.richmillionaire.models.Transaction;
import com.richmillionaire.richmillionaire.models.User;
import com.richmillionaire.richmillionaire.models.Wallet;


public interface WalletService {
    Wallet findById(String publicKey) throws Exception;
    List<Wallet> findAll() throws Exception;
    Wallet save(Wallet wallet) throws Exception;
    void deleteById(String publicKey) throws Exception;    
    Wallet createWallet() throws Exception;
    Wallet createWalletForUser(User user) throws Exception;
    List<Wallet> findByUser(User user) throws Exception;
    List<Wallet> findByUserId(UUID userId) throws Exception;
    Transaction transfer(String fromPublicKey, String toPublicKey, double amount) throws Exception;
}
