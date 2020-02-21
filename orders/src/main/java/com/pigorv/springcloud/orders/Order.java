package com.pigorv.springcloud.orders;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Order {
    private List<String> productNames;
    private Long ownerId;
    private Long totalPrice;
}

