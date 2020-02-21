package com.pigorv.springcloud.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

@RequestMapping("/orders")
@RestController
public class OrdersController {
    @Autowired
    RestTemplate restTemplate;

    @Value("${services.products.url}")
    private String productsUrl;
    @Value("${services.users.url}")
    private String usersUrl;

    private String usersMoneyUrlPattern;

    @PostMapping
    public Order createNewOrder(@RequestBody List<String> productNames, @RequestParam Long ownerId) {
        return new Order();
    }

    @GetMapping
    public String getProducts() {
        final ProductDto[] forObject = restTemplate.getForObject(productsUrl, ProductDto[].class);
        return forObject[0].toString();
    }

    @PostConstruct
    private void init() {
        usersMoneyUrlPattern = usersUrl + "/%s/money";
    }

    @Bean
    private RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
