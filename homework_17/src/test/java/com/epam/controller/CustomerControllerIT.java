package com.epam.controller;

import com.epam.dto.AccountDto;
import com.epam.dto.AccountUpdateDto;
import com.epam.dto.AddressDto;
import com.epam.dto.CustomerDto;
import com.epam.dto.CustomerNamesUpdateDto;
import com.epam.dto.CustomerUpdateDto;
import com.epam.entity.Account;
import com.epam.entity.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class CustomerControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    public void clearDb(){
        mongoTemplate.dropCollection(Customer.class);
    }

    @Test
    public void shouldReturn200AndFindAllCustomes() throws Exception {
        // GIVEN
        Customer customer = new Customer();

        customer.setFirstName("Ivan");

        mongoTemplate.save(customer);

        // WHEN
        val request = get("/customer");

        // THEN
        mockMvc.perform(request)
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn200AndFindCustomerById() throws Exception {
        // GIVEN
        Customer customer = new Customer();

        customer.setFirstName("Ivan");

        mongoTemplate.save(customer);

        val id = customer.getId();

        // WHEN
        val request = get("/customer/" + id);

        // THEN
        mockMvc.perform(request)
                .andExpect(jsonPath("$.id").value(customer.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn200AndFindAllCustomesByNames() throws Exception {
        // GIVEN
        Customer customer = new Customer();

        customer.setFirstName("Ivan");

        customer.setLastName("Ivanov");

        mongoTemplate.save(customer);

        CustomerNamesUpdateDto customerNamesUpdateDto = new CustomerNamesUpdateDto();

        customerNamesUpdateDto.setFirstName("Ivan");

        customerNamesUpdateDto.setLastName("Ivanov");

        // WHEN
        val request = get("/customer/names")
                .content(objectMapper.writeValueAsString(customerNamesUpdateDto))
                .contentType(MediaType.APPLICATION_JSON);

        // THEN
        mockMvc.perform(request)
                .andExpect(jsonPath("$.[0].id").value(customer.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn200AndCreateCustomer() throws Exception {
        // GIVEN
        CustomerDto customerDto = new CustomerDto();

        customerDto.setFirstName("Ivan");

        customerDto.setAccount(new AccountDto(
                "1234",
                "MyAcc",
                "01-01-2021"
        ));

        customerDto.setAddress(new AddressDto(
                "line1",
                "line2",
                551
        ));

        // WHEN
        val request = post("/customer")
                .content(objectMapper.writeValueAsString(customerDto))
                .contentType(MediaType.APPLICATION_JSON);

        // THEN
        mockMvc.perform(request)
                .andExpect(status().isOk());

        val customer = mongoTemplate.findAll(Customer.class).get(0);

        assertThat(customer.getFirstName(), is("Ivan"));
        assertThat(customer.getAccount().getCardNumber(), is("1234"));
    }

    @Test
    public void shouldReturn200AndUpdateCustomer() throws Exception {
        // GIVEN
        Customer customer = new Customer();

        customer.setFirstName("Ivan");
        customer.setLastName("Ivanov");

        mongoTemplate.save(customer);

        CustomerUpdateDto customerUpdateDto = new CustomerUpdateDto();

        customerUpdateDto.setFirstName("Petr");
        customerUpdateDto.setLastName("Petrov");

        val id = customer.getId();

        // WHEN
        val request = patch("/customer/" + id)
                .content(objectMapper.writeValueAsString(customerUpdateDto))
                .contentType(MediaType.APPLICATION_JSON);

        // THEN
        mockMvc.perform(request)
                .andExpect(status().isOk());

        val customerDb = mongoTemplate.findAll(Customer.class).get(0);

        assertThat(customerDb.getFirstName(), is("Petr"));
    }

    @Test
    public void shouldReturn200AndUpdateAccount() throws Exception {
        // GIVEN
        Customer customer = new Customer();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        customer.setFirstName("Ivan");
        customer.setLastName("Ivanov");

        customer.setAccount(new Account(
                "1234",
                "MyAcc",
                LocalDate.parse("01-01-2021", formatter)
        ));

        mongoTemplate.save(customer);

        AccountUpdateDto accountUpdateDto = new AccountUpdateDto(
                "4321",
                "SomeAcc",
                "01-05-2021"
        );

        val id = customer.getId();

        // WHEN
        val request = patch("/customer/" + id + "/account")
                .content(objectMapper.writeValueAsString(accountUpdateDto))
                .contentType(MediaType.APPLICATION_JSON);

        // THEN
        mockMvc.perform(request)
                .andExpect(status().isOk());

        val customerDb = mongoTemplate.findAll(Customer.class).get(0);

        assertThat(customerDb.getAccount().getCardNumber(), is("4321"));
        assertThat(customerDb.getAccount().getNameOnAccount(), is("SomeAcc"));
        assertThat(customerDb.getAccount().getExpirationDate(), is(LocalDate.parse("01-05-2021", formatter)));
    }

    @Test
    public void shouldReturn200AndFindAllExpiredCards() throws Exception {
        // GIVEN
        Customer customer = new Customer();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        customer.setFirstName("Ivan");
        customer.setLastName("Ivanov");

        customer.setAccount(new Account(
                "1234",
                "MyAcc",
                LocalDate.parse("01-01-2021", formatter)
        ));

        mongoTemplate.save(customer);

        // WHEN
        val request = get("/customer/account/expired");

        // THEN
        mockMvc.perform(request)
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn200AndFindCustomerByCardNumber() throws Exception {
        // GIVEN
        Customer customer = new Customer();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        customer.setFirstName("Ivan");
        customer.setLastName("Ivanov");

        customer.setAccount(new Account(
                "1234",
                "MyAcc",
                LocalDate.parse("01-01-2021", formatter)
        ));

        mongoTemplate.save(customer);

        val number = customer.getAccount().getCardNumber();

        // WHEN
        val request = get("/customer/account/" + number);

        // THEN
        mockMvc.perform(request)
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"))
                .andExpect(status().isOk());
    }
}
