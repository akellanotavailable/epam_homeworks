package com.epam.repository;

import com.epam.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    List<Customer> findByFirstNameAndLastName(String firstName, String lastName);

    List<Customer> findByAddressFirstLine(String line);

    Customer findByAccountCardNumber(String cardNumber);

    List<Customer> findByAccountExpirationDateLessThan(LocalDate date);
}
