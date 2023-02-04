package com.backend.uberclone.repository;

import com.backend.uberclone.model.Ride;
import com.backend.uberclone.model.RideStatus;
import com.backend.uberclone.model.User;
import com.backend.uberclone.service.RideService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RideRepositoryTests {
    @Autowired
    private UserRepository<User> userRepository;

   @Test
    public void findByIdAndStatusAndDriverId(){
       User u = userRepository.findOneById(1);
       assertEquals("bubibubisa@gmail.com",u.getEmail());

   }
}
