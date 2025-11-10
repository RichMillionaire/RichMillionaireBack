package com.richmillionaire.richmillionaire.dao.implementation;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.richmillionaire.richmillionaire.dao.WalletDAO;
import com.richmillionaire.richmillionaire.models.Wallet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class WalletDAOImpl implements WalletDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Wallet save(Wallet wallet) throws Exception {
        return entityManager.merge(wallet); 
    }

    @Override
    public List<Wallet> findAll() throws Exception {
        return entityManager.createQuery("SELECT w FROM Wallet w", Wallet.class)
                .getResultList();
    }

    @Override
    public Wallet findById(String publicKey) throws Exception {
        TypedQuery<Wallet> query = entityManager.createQuery(
            "SELECT w FROM Wallet w WHERE w.publicKey = :key", 
            Wallet.class
        );
        System.out.println("### DAO: Recherche de clé: [" + publicKey + "]");
        query.setParameter("key", publicKey);
        
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new Exception("Wallet non trouvé avec la clé publique: " + publicKey);
        }
    }

    @Override
    public void deleteById(String publicKey) throws Exception {
        Wallet wallet = findById(publicKey);
        if (wallet != null) {
            entityManager.remove(wallet);
        }
    }
}