package com.richmillionaire.richmillionaire.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.richmillionaire.richmillionaire.dao.TransactionDAO;
import com.richmillionaire.richmillionaire.models.Transaction;
import com.richmillionaire.richmillionaire.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService{

    private final TransactionDAO transactionDAO;

    public TransactionServiceImpl(TransactionDAO transactionDAO){
        this.transactionDAO = transactionDAO;
    }
    @Override
    public Transaction findById(String id) throws Exception {
        return transactionDAO.findById(id);
    }

    @Override
    public List<Transaction> findAll() throws Exception {
        return transactionDAO.findAll();
    }

    @Override
    public Transaction save(Transaction transaction) throws Exception {
        return transactionDAO.save(transaction);
    }

    @Override
    public void deleteById(String id) throws Exception {
        transactionDAO.deleteById(id);
    }
    
}
