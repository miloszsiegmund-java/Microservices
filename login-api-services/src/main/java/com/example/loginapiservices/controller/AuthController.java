package com.example.loginapiservices.controller;

import com.example.loginapiservices.payload.request.LoginRequest;
import com.example.loginapiservices.security.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    JwtUtils jwtUtils;

    private static final String TOKEN = "token";
    private static final String URL = "http://localhost:9090/api/token";

    @ApiOperation(value = "Authentication user by username and password")
    @ApiResponse(code = 401 , message = "Unauthorized")
    @PostMapping("/signin")
    public void authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws IOException {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        HashMap<String, String> responseBody = new HashMap<>();
        responseBody.put(TOKEN, jwt);
        new ObjectMapper().writeValue(response.getWriter(), responseBody);
        restTemplate.postForEntity(URL, responseBody, Map.class);

    }
}
