package com.example.loginapiservices.controller;

import com.example.loginapiservices.domain.dto.UserDto;
import com.example.loginapiservices.mapper.UserMapper;
import com.example.loginapiservices.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping
    public UserDto register(@RequestBody UserDto userDto) {
        return userMapper.toDto(userService.save(userMapper.toModel(userDto)));
    }

    @GetMapping
    public List<UserDto> listUser(){
        return userMapper.toListDto(userService.findAll());
    }
}
