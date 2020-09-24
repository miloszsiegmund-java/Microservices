package com.example.usertokenservices.repository;

import com.example.usertokenservices.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByEmail(String email);

    Optional<Token> findByToken(String token);
}
