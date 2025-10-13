package com.richmillionaire.richmillionaire.services;

import java.util.List;

import com.richmillionaire.richmillionaire.models.Wallet;


public interface WalletService {
    Wallet findById(String publicKey) throws Exception;
    List<Wallet> findAll() throws Exception;
    Wallet save(Wallet wallet) throws Exception;
    void deleteById(String publicKey) throws Exception;    
}
