package com.backend.uberclone.repository;


import com.backend.uberclone.model.Driver;
import com.backend.uberclone.model.Ride;
import com.backend.uberclone.model.RideStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest

public class DriverRepositoryTests {

    @Autowired
    DriverRepository driverRepository;

    @Test
    public void findRideById(){
        Driver d = driverRepository.findOneById(3);
        Assertions.assertNotNull(d);
        Assertions.assertEquals("stevaszumza@gmail.com",d.getUsername());

    }

    @Test
    public void saveDriver(){
        Driver driver = new Driver();

        driver.setEmail("estebanito@gmail.com");
        driver.setName("Stefan");
        driver.setId(999);
        Driver newDriver = driverRepository.save(driver);

        assertNotNull(newDriver);
        assertEquals("Stefan", newDriver.getName());
    }
}
