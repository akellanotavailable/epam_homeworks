package com.epam.springcloud;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/users")
@RestController
public class UsersController {
    @Autowired
    RestTemplate restTemplate;

    @PostMapping
    public User createNewUser(@RequestParam String name) {
        var user = new User();
        user.setName(name);
        user.setId(RandomUtils.nextLong());
        user.setDeposit(1000L);

        return user;
    }

    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
