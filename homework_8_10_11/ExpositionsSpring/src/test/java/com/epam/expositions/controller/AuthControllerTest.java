package com.epam.expositions.controller;

import com.epam.expositions.entity.Role;
import com.epam.expositions.entity.User;
import com.epam.expositions.service.AuthService;
import com.epam.expositions.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private final UserService userService = mock(UserService.class);

    private final AuthService authService = mock(AuthService.class);

    private final AuthController authController = new AuthController(authService);

    private MockMvc mockMvc;

    private String email;

    private String login;

    private String password;

    private User user;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setViewResolvers(viewResolver)
                .build();

        long id = 99L;
        email = "ad@ad.com";
        login = "pepepe";
        password = "123";
        Role role = Role.ADMIN;
        user = User
                .builder()
                .id(id)
                .email(email)
                .password(password)
                .login(login)
                .role(role)
                .build();
    }

    @Test
    public void getLoginPageShouldReturnLoginPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/login")

                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

}
