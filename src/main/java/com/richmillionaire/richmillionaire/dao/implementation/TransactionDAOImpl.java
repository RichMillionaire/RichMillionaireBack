package com.richmillionaire.richmillionaire.dao.implementation;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.richmillionaire.richmillionaire.dao.TransactionDAO;
import com.richmillionaire.richmillionaire.models.Transaction;
import com.richmillionaire.richmillionaire.models.Wallet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class TransactionDAOImpl implements TransactionDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Transaction transaction) throws Exception {
        entityManager.persist(transaction);    
    }

    @Override
    public List<Transaction> findAll() throws Exception {
        return entityManager.createQuery("SELECT t FROM Transaction t", Transaction.class)
                .getResultList();
    }

    @Override
    public Transaction findById(String id) throws Exception {
        return entityManager.find(Transaction.class, id);
    }

    @Override
    public void deleteById(String id) throws Exception {
        Transaction transaction = findById(id);
        if (id != null) {
            entityManager.remove(transaction);
        }
    }

    
}
