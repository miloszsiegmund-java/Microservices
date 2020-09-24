package com.example.usertokenservices.controller;

import com.example.usertokenservices.domain.Token;
import com.example.usertokenservices.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping
    public Token saveToken(@RequestBody Token token) {
        return tokenService.save(token);
    }

    @GetMapping
    public List<Token> getAll(){
        return tokenService.findAll();
    }

    @GetMapping("/{token}")
    public void isTokenValid(@PathVariable String token){
        tokenService.isTokenValid(token);
    }
}
