package com.epam.service;

import com.epam.dto.UserDto;

public interface UserService {
    UserDto create(UserDto dto);
    UserDto get(String name);
    UserDto update(UserDto dto);
    void delete(String id);
}
