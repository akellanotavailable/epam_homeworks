package com.epam.springcloud.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/products")
@RestController
public class ProductsController {
    @Autowired
    ProductsRepository repository;

    @GetMapping
    public List<Product> getProducts() {
        return repository.getAll();
    }
}
