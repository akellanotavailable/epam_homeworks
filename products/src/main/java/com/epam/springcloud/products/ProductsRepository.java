package com.epam.springcloud.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductsRepository {
    private final ProductsFactory factory;
    private List<Product> productList = new ArrayList<>();

    @Autowired
    public ProductsRepository(ProductsFactory factory) {
        this.factory = factory;
    }

    public void add(Product newProduct) {
        productList.add(newProduct);
    }

    public Optional<Product> getByName(String name) {
        return productList.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst();
    }

    public List<Product> getAll() {
        return new ArrayList<>(productList);
    }

    @PostConstruct
    private void init() {
        add(factory.createProduct());
        add(factory.createProduct());
        add(factory.createProduct());
        add(factory.createProduct());
        add(factory.createProduct());
        add(factory.createProduct());
        add(factory.createProduct());
        add(factory.createProduct());
        add(factory.createProduct());
    }
}
