package com.epam.springcloud.products;

import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class ProductsController {
    @Autowired
    ProductsRepository repository;

    @PostMapping
    public Product createProduct() {
        var name = RandomStringUtils.randomAlphabetic(16);
        var quantity = RandomUtils.nextLong(1, 10);
        repository.add(name, quantity);

        return new Product(name, quantity);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return repository.getAll();
    }

    @GetMapping("/{productName}")
    public Product getProduct(@PathVariable String productName) {
        return new Product(
                productName,
                repository.getQuantity(productName)
        );
    }

    @PutMapping("/{productName}")
    public Product removeOneProduct(@PathVariable String productName) {
        Long quantity = repository.getQuantity(productName);
        if (quantity <= 0) {
            throw new NotFoundProductException();
        }

        quantity = repository.removeOneByName(productName);

        return new Product(productName, quantity);
    }
}
