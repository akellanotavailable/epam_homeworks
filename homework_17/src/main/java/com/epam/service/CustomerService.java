package com.epam.service;

import com.epam.dto.AccountUpdateDto;
import com.epam.dto.CustomerDto;
import com.epam.dto.CustomerNamesUpdateDto;
import com.epam.dto.CustomerUpdateDto;
import com.epam.entity.Account;
import com.epam.entity.Address;
import com.epam.entity.Customer;
import com.epam.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(String id) {
        return customerRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void save(CustomerDto customerDto) {
        Customer customer = new Customer();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());

        customer.setAccount(new Account(
                customerDto.getAccount().getCardNumber(),
                customerDto.getAccount().getNameOnAccount(),
                LocalDate.parse(customerDto.getAccount().getExpirationDate(), formatter)
        ));

        customer.setAddress(new Address(
                customerDto.getAddress().getFirstLine(),
                customerDto.getAddress().getSecondLine(),
                customerDto.getAddress().getCountryCode()
        ));

        customerRepository.save(customer);
    }

    public void updateCustomer(CustomerUpdateDto customerUpdateDto, String id) {
        Customer customer = findById(id);

        customer.setFirstName(customerUpdateDto.getFirstName());
        customer.setLastName(customerUpdateDto.getLastName());

        customerRepository.save(customer);
    }

    public void updateAccount(AccountUpdateDto accountUpdateDto, String id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Customer customer = findById(id);

        Account account = new Account(
                accountUpdateDto.getCardNumber(),
                accountUpdateDto.getNameOnAccount(),
                LocalDate.parse(accountUpdateDto.getExpirationDate(), formatter)
        );

        customer.setAccount(account);

        customerRepository.save(customer);
    }

    public List<Customer> findByExpirationDate() {
        return customerRepository.findByAccountExpirationDateLessThan(LocalDate.now());
    }

    public List<Customer> findByFirstNameAndLastName(CustomerNamesUpdateDto customerNamesUpdateDto) {
        return customerRepository.findByFirstNameAndLastName(
                customerNamesUpdateDto.getFirstName(),
                customerNamesUpdateDto.getLastName()
        );
    }

    public Customer findByCardNumber(String number) {
        return customerRepository.findByAccountCardNumber(number);
    }
}
