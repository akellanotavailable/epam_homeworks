package com.epam.springcloud.products;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductsFactory {

    public Product createProduct() {
        var product = new Product();

        product.setName(RandomStringUtils.random(10, true, false));
        product.setPrice(RandomUtils.nextLong(1, 100));

        return product;
    }
}
