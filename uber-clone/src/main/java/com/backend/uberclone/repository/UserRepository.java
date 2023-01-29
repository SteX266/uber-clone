package com.backend.uberclone.repository;

import com.backend.uberclone.model.Driver;
import com.backend.uberclone.model.Role;
import com.backend.uberclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository<T extends User> extends JpaRepository<T, Integer> {

    boolean existsByEmail(String email);

    T findOneById(Integer id);

    User findUserByEmailAndBannedFalseAndEnabledTrueAndDeletedFalse(String email);

    User findOneByEmail(String email);

    User findByResetPasswordToken(String token);

}
