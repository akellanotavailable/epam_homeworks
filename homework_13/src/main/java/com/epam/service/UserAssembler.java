package com.epam.service;

import com.epam.dto.UserDto;
import com.epam.entity.User;

public interface UserAssembler {
    User assemble(UserDto userDto);
    UserDto assemble(User user);
}
