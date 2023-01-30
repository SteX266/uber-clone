package com.backend.uberclone.repository;

import com.backend.uberclone.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findOneByEmail(String email);

    Set<Customer> findAllByEmailIn(List<String> emails);

}
