package com.epam.springcloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequestMapping
@RestController
public class UsersController {
    private Map<String, User> registeredUsers = new HashMap<>();

    @Autowired
    RestTemplate restTemplate;

    @PostMapping
    public User createUser() {
        var user = new User(RandomStringUtils.randomAlphabetic(13));
        registeredUsers.put(user.getName(), user);

        return user;
    }

    @GetMapping("/{userName}")
    public ResponseEntity<User> getUser(@PathVariable String userName) {
        User user = registeredUsers.get(userName);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{userName}/products")
    @HystrixCommand(fallbackMethod = "getDefaultProducts")
    public List getProductsByUser(@PathVariable String userName) {
        return restTemplate.getForObject("http://orders/users/" + userName, List.class);
    }

    private ArrayList<String> getDefaultProducts(String value) {
        return new ArrayList<>(List.of("One", "Two", "Three"));
    }

    @Bean
    @LoadBalanced
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
