package com.example.user.service;

import com.example.user.model.User;

import java.util.List;

public interface UserService {
    List<User> fetchAllUsers();
    User fetchUser(Long userId);
    User updateUser(Long id, User user);
}
