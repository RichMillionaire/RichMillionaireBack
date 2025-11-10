package com.richmillionaire.richmillionaire.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.Base64;
import java.security.Signature;
import org.springframework.stereotype.Service;

import com.richmillionaire.richmillionaire.dao.WalletDAO;
import com.richmillionaire.richmillionaire.dto.CreateBlockRequest;
import com.richmillionaire.richmillionaire.models.User;
import com.richmillionaire.richmillionaire.models.Wallet;
import com.richmillionaire.richmillionaire.models.Transaction;
import com.richmillionaire.richmillionaire.models.Block;
import com.richmillionaire.richmillionaire.services.WalletService;

import jakarta.transaction.Transactional;

import com.richmillionaire.richmillionaire.services.BlockService;
import com.richmillionaire.richmillionaire.services.TransactionService;
import com.richmillionaire.richmillionaire.core.Blockchain;

@Service
public class WalletServiceImpl implements WalletService{

    private final WalletDAO walletDAO;
    private final BlockService blockService;
    private final TransactionService transactionService;

    public WalletServiceImpl(WalletDAO walletDAO, BlockService blockService, TransactionService transactionService){
        this.walletDAO = walletDAO; 
        this.transactionService = transactionService;
        this.blockService = blockService;
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
    @Override
    @Transactional
    public Wallet createWallet() throws Exception {
        Wallet newWallet = new Wallet(); 
        newWallet.setBalance(500.0);
        return walletDAO.save(newWallet);
    }

    @Override
    @Transactional
    public Wallet createWalletForUser(User user) throws Exception {
        Wallet newWallet = new Wallet(); 
        newWallet.setBalance(500.0);
        newWallet.setUser(user);
        return walletDAO.save(newWallet);
    }

    @Override
    public List<Wallet> findByUser(User user) throws Exception {
        return walletDAO.findByUser(user);
    }

    @Override
    public List<Wallet> findByUserId(UUID userId) throws Exception {
        return walletDAO.findByUserId(userId);
    }
    @Override
    @Transactional
    public Transaction transfer(String fromPublicKey, String toPublicKey, double amount) throws Exception {

        Wallet fromWallet = walletDAO.findById(fromPublicKey);
        
        if (fromWallet.getBalance() < amount) {
            throw new Exception("T'as pas d'argent (solde BDD) !! Solde: " + fromWallet.getBalance());
        }
        if (amount <= 0) {
            throw new Exception("Montant invalide");
        }

        CreateBlockRequest blockRequest = new CreateBlockRequest();
        blockRequest.setData("Bloc pour la transaction de " + fromPublicKey);
        blockRequest.setMinedBy(fromPublicKey);

        Block minedBlock = blockService.save(blockRequest);
        byte[] toAddressBytes = Base64.getDecoder().decode(toPublicKey);
        Signature signature = Signature.getInstance("SHA256withDSA");

        Transaction tx = new Transaction(
            fromWallet,
            toAddressBytes,
            amount,
            minedBlock,
            signature
        );
        
        Transaction savedTx = transactionService.save(tx);
        
        minedBlock.setData("Transaction ID: " + savedTx.getId().toString());
        blockService.update(minedBlock); 

        Wallet toWallet = walletDAO.findById(toPublicKey);
        fromWallet.setBalance(fromWallet.getBalance() - amount);
        toWallet.setBalance(toWallet.getBalance() + amount);
        
        walletDAO.save(fromWallet);
        walletDAO.save(toWallet);

        return savedTx;
    }
}
