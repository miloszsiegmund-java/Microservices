package com.example.usertokenservices.controller;

import com.example.usertokenservices.domain.Token;
import com.example.usertokenservices.response.ApiResponse;
import com.example.usertokenservices.service.TokenService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    private static final String TOKEN_VALID = "Token is valid";

    @ApiOperation(value = "Save token")
    @PostMapping
    public Token saveToken(@RequestBody Token token) {
        return tokenService.save(token);
    }

    @ApiOperation(value = "Get all token from database")
    @GetMapping
    public List<Token> getAll(){
        return tokenService.findAll();
    }


    @ApiOperation(value = "Validate token")
    @GetMapping("/{token}")
    public ResponseEntity<?> isTokenValid(@PathVariable String token){
        tokenService.isTokenValid(token);
        return new ResponseEntity<>(new ApiResponse(true, TOKEN_VALID),
                HttpStatus.OK);
    }
}
