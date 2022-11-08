package com.backend.uberclone.repository;

import com.backend.uberclone.model.Customer;
import com.backend.uberclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository<T extends User> extends JpaRepository<T, Integer> {

    boolean existsByEmail(String email);

    T findOneById(Integer id);

    User findUserByEmailAndBannedFalseAndEnabledTrueAndDeletedFalse(String email);

}
