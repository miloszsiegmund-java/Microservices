package com.example.usertokenservices.service;

import com.example.usertokenservices.domain.Token;

import java.util.List;

public interface TokenService {

    Token save(Token token);

    List<Token> findAll();

    void isTokenValid(String token);
}
