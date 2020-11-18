package com.example.user.persistence.repository;

import com.example.user.persistence.entity.UserEntity;

import java.util.Optional;

public interface UserDAO {
    Iterable<UserEntity> fetchAllUsers();
    Optional<UserEntity> fetchUserByIdWithFallback(Long userId);
    Optional<UserEntity> fetchUserById(Long userId);
    UserEntity updateUser(UserEntity userEntity);
}
