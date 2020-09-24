package com.example.loginapiservices.service.impl;

import com.example.loginapiservices.domain.model.User;
import com.example.loginapiservices.repository.RoleRepository;
import com.example.loginapiservices.repository.UserRepository;
import com.example.loginapiservices.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        roleRepository.findByName("ROLE_USER").ifPresent(role -> user.setRoles(Collections.singleton(role)));
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
