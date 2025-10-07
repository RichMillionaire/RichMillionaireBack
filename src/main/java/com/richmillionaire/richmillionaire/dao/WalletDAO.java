package com.richmillionaire.richmillionaire.dao;

import java.util.List;

import com.richmillionaire.richmillionaire.models.Wallet;

public interface WalletDAO {
    void save(Wallet wallet) throws Exception;

    List<Wallet> findAll() throws Exception;

    Wallet findById(String publicKey) throws Exception;

    void deleteById(String publicKey) throws Exception;
}