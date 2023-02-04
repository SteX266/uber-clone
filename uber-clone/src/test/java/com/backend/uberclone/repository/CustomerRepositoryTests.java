package com.backend.uberclone.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.backend.uberclone.model.Customer;
import com.backend.uberclone.model.Driver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void findAllByEmailIn(){
        List<String> emails = new ArrayList<>();
        emails.add("serfezev@gmail.com");
        emails.add("esteban@gmail.com");
        Set<Customer> customers = this.customerRepository.findAllByEmailIn(emails);

        assertEquals(2, customers.size());


    }
    @Test
    public void saveCustomer(){

        Customer customer = new Customer();

        customer.setEmail("estebanito@gmail.com");
        customer.setName("Stefan");
        customer.setId(999);
        Customer newCustomer = customerRepository.save(customer);

        assertNotNull(newCustomer);
        assertEquals("Stefan", newCustomer.getName());
    }
}
