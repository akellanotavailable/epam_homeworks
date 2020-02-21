package com.epam.springcloud.products;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Product {
    private String name;
    private Long price;
}
