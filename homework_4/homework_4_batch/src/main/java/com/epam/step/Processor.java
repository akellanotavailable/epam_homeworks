package com.epam.step;

import com.epam.entity.User;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;

public class Processor implements ItemProcessor<User, User> {

    @Override
    public User process(User user) {
        if (user.getBalance().compareTo(BigDecimal.TEN) < 0) {
            return user;
        }
        return null;
    }
}
