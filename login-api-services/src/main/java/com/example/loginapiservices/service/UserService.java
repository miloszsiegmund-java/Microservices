package com.example.loginapiservices.service;

import com.example.loginapiservices.domain.model.User;

import java.util.List;

public interface UserService {

    User save(User user);

    List<User> findAll();
}
