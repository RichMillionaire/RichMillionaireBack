package com.richmillionaire.richmillionaire.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.richmillionaire.richmillionaire.models.Transaction;
import com.richmillionaire.richmillionaire.models.Wallet;
import com.richmillionaire.richmillionaire.services.TransactionService;
import com.richmillionaire.richmillionaire.services.WalletService;

@CrossOrigin
@RestController
@RequestMapping(("/wallets"))
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }
    @GetMapping("")
    public List<Wallet> getAllTransactions() throws Exception{
        return walletService.findAll();
    }
    @GetMapping("/{id}")
    public Wallet getTransaction(String id) throws Exception {
        return walletService.findById(id);
    }
    @PostMapping("")
    public Wallet createBlock(Wallet wallet) throws Exception {
        return walletService.save(wallet);
    }
    @DeleteMapping("/{id}")
    public void deleteBlock(String id) throws Exception {
        walletService.deleteById(id);
    }
}
