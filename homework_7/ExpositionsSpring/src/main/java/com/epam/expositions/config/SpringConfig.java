package com.epam.expositions.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;


@Configuration
public class SpringConfig {

    @Bean
    @SneakyThrows
    public Connection connection(
            @Value("${url}") String url,
            @Value("${usernameDB}") String username,
            @Value("${password}") String password
    ) {
        return DriverManager.getConnection(url, username, password);
    }


}
