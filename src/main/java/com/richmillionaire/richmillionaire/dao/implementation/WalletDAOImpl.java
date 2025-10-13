package com.richmillionaire.richmillionaire.dao.implementation;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.richmillionaire.richmillionaire.dao.WalletDAO;
import com.richmillionaire.richmillionaire.models.Wallet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class WalletDAOImpl implements WalletDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Wallet save(Wallet wallet) throws Exception {
        entityManager.persist(wallet);
        return wallet;
    }

    @Override
    public List<Wallet> findAll() throws Exception {
        return entityManager.createQuery("SELECT w FROM Wallet w", Wallet.class)
                .getResultList();
    }

    @Override
    public Wallet findById(String publicKey) throws Exception {
        return entityManager.find(Wallet.class, publicKey);
    }

    @Override
    public void deleteById(String publicKey) throws Exception {
        Wallet wallet = findById(publicKey);
        if (wallet != null) {
            entityManager.remove(wallet);
        }
    }

}
