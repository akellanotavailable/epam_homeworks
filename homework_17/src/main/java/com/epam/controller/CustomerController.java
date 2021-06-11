package com.epam.controller;

import com.epam.dto.AccountUpdateDto;
import com.epam.dto.CustomerDto;
import com.epam.dto.CustomerNamesUpdateDto;
import com.epam.dto.CustomerUpdateDto;
import com.epam.entity.Customer;
import com.epam.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getAll() {
        return customerService.findAll();
    }

    @GetMapping("{id}")
    public Customer getCustomer(@PathVariable String id) {
        return customerService.findById(id);
    }

    @GetMapping("names")
    public List<Customer> getCustomersByFirstNameAndLastName(@RequestBody CustomerNamesUpdateDto customerNamesUpdateDto){
        return customerService.findByFirstNameAndLastName(customerNamesUpdateDto);
    }

    @PostMapping
    public void create(@RequestBody CustomerDto customerDto) {
        customerService.save(customerDto);
    }

    @PatchMapping("{id}")
    public void update(@RequestBody CustomerUpdateDto customerUpdateDto, @PathVariable String id){
        customerService.updateCustomer(customerUpdateDto, id);
    }

    @PatchMapping("{id}/account")
    public void updateAccount(@RequestBody AccountUpdateDto accountUpdateDto, @PathVariable String id){
        customerService.updateAccount(accountUpdateDto, id);
    }

    @GetMapping("account/expired")
    public List<Customer> getByExpiredCards(){
        return customerService.findByExpirationDate();
    }

    @GetMapping("account/{number}")
    public Customer getByCardNumber(@PathVariable String number) {
        return customerService.findByCardNumber(number);
    }
}
