package com.richmillionaire.richmillionaire.services;

import java.util.List;

import com.richmillionaire.richmillionaire.models.Transaction;


public interface TransactionService {
    Transaction findById(String id) throws Exception;
    List<Transaction> findAll() throws Exception;
    Transaction save(Transaction transaction) throws Exception;
    void deleteById(String id) throws Exception;    
}
