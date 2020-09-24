package com.example.loginapiservices.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final RestTemplate restTemplate;

    private static final String EMAIL = "email";
    private static final String AUTHORITIES = "authorities";
    private static final String TOKEN = "token";
    private static final String URL = "http://localhost:9090/api/token";


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, RestTemplate restTemplate) {
        setAuthenticationManager(authenticationManager);
        setUsernameParameter(EMAIL);
        this.restTemplate = restTemplate;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
        Claims claims = new DefaultClaims()
                .setExpiration(expirationDate)
                .setSubject(authResult.getName());
        claims.put(AUTHORITIES, authResult.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")));

        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "test1111")
                .compact();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        HashMap<String, String> responseBody = new HashMap<>();
        responseBody.put(TOKEN, token);
        new ObjectMapper().writeValue(response.getWriter(), responseBody);
        restTemplate.postForEntity(URL, responseBody, Map.class);
    }
}
