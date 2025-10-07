package com.richmillionaire.richmillionaire.dao.impl;

import com.richmillionaire.richmillionaire.dao.UserDao;
import com.richmillionaire.richmillionaire.entity.UserEntity;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public class UserDaoJpa implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        try {
            TypedQuery<UserEntity> query = entityManager.createQuery(
                "SELECT u FROM UserEntity u LEFT JOIN FETCH u.roles WHERE u.username = :username",
                UserEntity.class
            );
            query.setParameter("username", username);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        try {
            TypedQuery<UserEntity> query = entityManager.createQuery(
                "SELECT u FROM UserEntity u LEFT JOIN FETCH u.roles WHERE u.email = :email",
                UserEntity.class
            );
            query.setParameter("email", email);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public UserEntity save(UserEntity user) {
        if (user.getId() == null) {
            entityManager.persist(user);
            return user;
        } else {
            return entityManager.merge(user);
        }
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        try {
            TypedQuery<UserEntity> query = entityManager.createQuery(
                "SELECT u FROM UserEntity u LEFT JOIN FETCH u.roles WHERE u.id = :id",
                UserEntity.class
            );
            query.setParameter("id", id);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(u) FROM UserEntity u WHERE u.username = :username",
            Long.class
        );
        query.setParameter("username", username);
        return query.getSingleResult() > 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(u) FROM UserEntity u WHERE u.email = :email",
            Long.class
        );
        query.setParameter("email", email);
        return query.getSingleResult() > 0;
    }
}
