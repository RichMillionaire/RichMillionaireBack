package com.richmillionaire.richmillionaire.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.richmillionaire.richmillionaire.models.Transaction;
import com.richmillionaire.richmillionaire.service.TransactionService;

@CrossOrigin
@RestController
@RequestMapping(("/transactions"))
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @GetMapping("")
    public List<Transaction> getAllTransactions() throws Exception{
        return transactionService.findAll();
    }
    @GetMapping("/{id}")
    public Transaction getTransaction(String id) throws Exception {
        return transactionService.findById(id);
    }
    @PostMapping("")
    public Transaction createBlock(Transaction transaction) throws Exception {
        return transactionService.save(transaction);
    }
    @DeleteMapping("/{id}")
    public void deleteBlock(String id) throws Exception {
        transactionService.deleteById(id);
    }
}
