package com.epam.expositions.service;

import com.epam.expositions.entity.Role;
import com.epam.expositions.entity.User;
import com.epam.expositions.repository.UserRepository;
import com.epam.expositions.service.impl.UserServiceImpl;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private final UserRepository userRepository = mock(UserRepository.class);

    private final UserService userService = new UserServiceImpl(userRepository);

    private long id;

    private String email;

    private String login;

    private List<User> users;

    private User user;

    @Before
    public void setUp() {
        id = 99L;
        email = "qwer";
        user = User
                .builder()
                .id(id)
                .email(email)
                .password("1")
                .login(login)
                .role(Role.USER)
                .build();

        users = new ArrayList<>();
        users.add(user);
        users.add(User
                .builder()
                .id(100L)
                .login("q")
                .email("q")
                .password("1")
                .role(Role.ADMIN)
                .build());
    }

    @Test
    public void shouldFindUserById() {
        User user = this.user;
        when(userRepository.findById(id)).thenReturn(Optional.of(user));


        User u = userService.findById(id);
        verify(userRepository).findById(id);


        assertThat(u, hasProperty("id", equalTo(id)));
    }

    @Test
    public void shouldFindUserByEmail() {
        User user = this.user;
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));


        User u = userService.findByEmail(email);
        verify(userRepository).findByEmail(email);


        assertThat(u, hasProperty("email", equalTo(email)));
    }

    @Test
    public void shouldFindAllUsers() {
        List<User> users = this.users;
        when(userRepository.findAll()).thenReturn(users);

        Collection<User> foundUsers = userService.findALL();

        verify(userRepository).findAll();
        assertThat(foundUsers, hasSize(2));

        users.forEach(
                user -> assertThat(foundUsers, Matchers.hasItem(Matchers.allOf(
                        hasProperty("id", Matchers.is(user.getId())),
                        hasProperty("login", Matchers.is(user.getLogin())),
                        hasProperty("email", Matchers.is(user.getEmail())),
                        hasProperty("password", Matchers.is(user.getPassword()))
                )))
        );
    }
}
