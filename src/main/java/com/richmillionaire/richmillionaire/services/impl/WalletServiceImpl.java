package com.richmillionaire.richmillionaire.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.richmillionaire.richmillionaire.dao.WalletDAO;
import com.richmillionaire.richmillionaire.models.Wallet;
import com.richmillionaire.richmillionaire.services.WalletService;

@Service
public class WalletServiceImpl implements WalletService{

    private final WalletDAO walletDAO;

    public WalletServiceImpl(WalletDAO walletDAO){
        this.walletDAO = walletDAO;
    }
    @Override
    public Wallet findById(String publicKey) throws Exception {
        return walletDAO.findById(publicKey);
    }

    @Override
    public List<Wallet> findAll() throws Exception {
        return walletDAO.findAll();
    }

    @Override
    public Wallet save(Wallet wallet) throws Exception {
        return walletDAO.save(wallet);
    }

    @Override
    public void deleteById(String publicKey) throws Exception {
        walletDAO.deleteById(publicKey);
    }
    
}
