package com.richmillionaire.richmillionaire.dao;

import com.richmillionaire.richmillionaire.entity.UserEntity;

import java.util.Optional;

public interface UserDao {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    UserEntity save(UserEntity user);

    Optional<UserEntity> findById(Long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
