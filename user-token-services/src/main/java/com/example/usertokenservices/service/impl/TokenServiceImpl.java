package com.example.usertokenservices.service.impl;

import com.example.usertokenservices.domain.Token;
import com.example.usertokenservices.repository.TokenRepository;
import com.example.usertokenservices.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Override
    public Token save(Token token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token.getToken())
                .getBody();
        String email = claims.getSubject();
        Optional<Token> optionalToken = tokenRepository.findByUsername(email);
        return optionalToken.map(t -> {
            t.setValidationDate(claims.getExpiration());
            t.setUsername(claims.getSubject());
            return tokenRepository.save(t);
        }).orElseGet(() -> {
            token.setValidationDate(claims.getExpiration());
            token.setUsername(claims.getSubject());
            return tokenRepository.save(token);
        });
    }

    @Override
    public List<Token> findAll() {
        return tokenRepository.findAll();
    }

    @Override
    public void isTokenValid(String token) {
        Optional<Token> optionalToken = tokenRepository.findByToken(token);
        optionalToken.map(t -> {
            if(LocalDateTime.ofInstant(t.getValidationDate().toInstant(), ZoneId.systemDefault()).isBefore(LocalDateTime.now())){
                throw new EntityNotFoundException(token);
            }
            return t;
        }).orElseThrow(() -> new EntityNotFoundException(token));
    }
}
