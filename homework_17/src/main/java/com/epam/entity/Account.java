package com.epam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private String cardNumber;

    private String nameOnAccount;

    private LocalDate expirationDate;
}
