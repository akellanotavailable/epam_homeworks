package com.epam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private String firstName;

    private String lastName;

    private AddressDto address;

    private AccountDto account;
}
