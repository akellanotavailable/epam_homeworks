package com.pigorv.springcloud.orders;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductDto {
    private String name;
    private Long price;
}