package com.richmillionaire.richmillionaire.dao;

import java.util.List;

import com.richmillionaire.richmillionaire.models.Transaction;

public interface TransactionDAO {
    Transaction save(Transaction transaction) throws Exception;

    List<Transaction> findAll() throws Exception;

    Transaction findById(String id) throws Exception;

    void deleteById(String id) throws Exception;
}
