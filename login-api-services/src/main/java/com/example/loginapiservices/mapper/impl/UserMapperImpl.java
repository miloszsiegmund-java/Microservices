package com.example.loginapiservices.mapper.impl;

import com.example.loginapiservices.domain.dto.UserDto;
import com.example.loginapiservices.domain.model.Role;
import com.example.loginapiservices.domain.model.User;
import com.example.loginapiservices.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDto toDto(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .id(user.getId())
                .password(user.getPassword())
                .roles(user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public User toModel(UserDto userDto) {
        return User.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }

    @Override
    public List<UserDto> toListDto(List<User> all) {
        return all.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
