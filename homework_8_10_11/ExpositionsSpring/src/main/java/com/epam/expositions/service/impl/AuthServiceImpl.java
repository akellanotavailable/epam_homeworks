package com.epam.expositions.service.impl;

import com.epam.expositions.annotation.Timed;
import com.epam.expositions.dto.LoginDTO;
import com.epam.expositions.entity.User;
import com.epam.expositions.exception.InvalidDataException;
import com.epam.expositions.exception.UserNotFoundException;
import com.epam.expositions.service.AuthService;
import com.epam.expositions.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Timed
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder encoder;

    @Override
    public String authenticate(LoginDTO data) throws InvalidDataException {

        if (data.getLogin() == null || data.getPassword() == null || data.getLogin().isBlank() || data.getPassword().isBlank()) {
            throw new InvalidDataException("Unexpected data input.");
        }
        User user;
        try {
            user = userService.findByLogin(data.getLogin());
            if (!encoder.matches(encoder.encode(data.getPassword()), user.getPassword())) {
                throw new InvalidDataException("Wrong password.");
            }
        } catch (UserNotFoundException e) {
            throw new InvalidDataException("No such login.");
        }

        return user.getRole().toString().toLowerCase(Locale.ROOT);
    }
}
