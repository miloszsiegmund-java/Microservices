package com.example.loginapiservices.mapper;

import com.example.loginapiservices.domain.dto.UserDto;
import com.example.loginapiservices.domain.model.User;

import java.util.List;

public interface UserMapper {

    UserDto toDto(User user);

    User toModel(UserDto userDto);

    List<UserDto> toListDto(List<User> all);
}
