package com.example.springInitDemo.services;

import com.example.springInitDemo.models.User;

public interface UserService {

    void registerUser(String username, int age);

    User findByUsername(String username);
}
