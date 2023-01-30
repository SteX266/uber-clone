package com.backend.uberclone.repository;

import com.backend.uberclone.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findOneByEmail(String email);

    Customer findOneById(Integer id);
}
