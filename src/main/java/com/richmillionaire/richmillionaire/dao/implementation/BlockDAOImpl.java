package com.richmillionaire.richmillionaire.dao.implementation;

import com.richmillionaire.richmillionaire.dao.BlockDAO;
import com.richmillionaire.richmillionaire.models.Block;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class BlockDAOImpl implements BlockDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Block save(Block block) {
        entityManager.persist(block);
        return block;
    }

    @Override
    public List<Block> findAll() {
        return entityManager.createQuery("SELECT b FROM Block b ORDER BY b.timeStamp DESC", Block.class)
                .getResultList();
    }

    @Override
    public Block findById(int id) {
        return entityManager.find(Block.class, id);
    }

    @Override
    public void deletebyId(int id) {
        Block block = findById(id);
        if (block != null) {
            entityManager.remove(block);
        }
    }
    @Override
    public Block findLastBlock() throws Exception {
        try {
            return entityManager.createQuery("FROM Block b ORDER BY b.timeStamp DESC", Block.class)
                .setMaxResults(1)
                .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null; 
        }
    }
}
