package com.richmillionaire.richmillionaire.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.richmillionaire.richmillionaire.models.Wallet;
import com.richmillionaire.richmillionaire.services.WalletService;
import com.richmillionaire.richmillionaire.models.Transaction;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@CrossOrigin
@RestController
@RequestMapping(("/wallets"))
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }
    @GetMapping("")
    public List<Wallet> getAllWallets() throws Exception{
        return walletService.findAll();
    }
    @GetMapping("/{id}")
    public Wallet getWallet(String id) throws Exception {
        return walletService.findById(id);
    }
    @PostMapping("")
    public Wallet createWallet() throws Exception {
        return walletService.createWallet();
    }
    @DeleteMapping("/{id}")
    public void deleteWallet(String id) throws Exception {
        walletService.deleteById(id);
    }

    @PostMapping("/transfer")
    public Transaction transfer(@RequestParam String fromPublicKey, @RequestParam String toPublicKey, @RequestParam double amount) throws Exception{
        return walletService.transfer(fromPublicKey, toPublicKey, amount);
    }
}
