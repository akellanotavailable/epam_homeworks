package com.epam.expositions.service;

import com.epam.expositions.entity.User;

import java.util.List;

public interface UserService{
    User findByLogin(String login);

    List<User> findALL();

    User create(User entity);

    User update(User entity, Long id);

    User findById(Long id);

    void deleteById(Long id);

    void delete(User entity);

    User findByEmail(String email);
}
