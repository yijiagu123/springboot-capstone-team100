package com.team100.springboot.service;

import com.team100.springboot.dto.UserDto;
import com.team100.springboot.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}

