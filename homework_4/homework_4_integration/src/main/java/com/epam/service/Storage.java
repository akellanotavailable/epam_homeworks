package com.epam.service;

import com.epam.entity.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Storage {

    public static List<Order> storage = new ArrayList<>();

    public Order addOrder(Order order) {
        storage.add(order);
        return order;
    }

}
