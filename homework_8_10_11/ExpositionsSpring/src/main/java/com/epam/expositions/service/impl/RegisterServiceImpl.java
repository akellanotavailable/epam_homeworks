package com.epam.expositions.service.impl;

import com.epam.expositions.dto.RegisterDTO;
import com.epam.expositions.entity.Role;
import com.epam.expositions.entity.User;
import com.epam.expositions.exception.InvalidDataException;
import com.epam.expositions.repository.UserRepository;
import com.epam.expositions.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public void register(RegisterDTO data) throws InvalidDataException {

        if (!data.getPassword().equals(data.getRePassword())) {
            throw new InvalidDataException("Passwords do not match.");
        }

        if (userRepository.findByLogin(data.getLogin()).isPresent()) {
            throw new InvalidDataException("This user already exists");
        }

        if (userRepository.findByEmail(data.getEmail()).isPresent()) {
            throw new InvalidDataException("This email is already registered");
        }

        userRepository.save(User.builder()
                .login(data.getLogin())
                .email(data.getEmail())
                .password(encoder.encode(data.getPassword()))
                .role(Role.USER)
                .build());
    }
}
