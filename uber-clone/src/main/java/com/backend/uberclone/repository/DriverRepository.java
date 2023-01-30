package com.backend.uberclone.repository;

import com.backend.uberclone.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Integer> {

    List<Driver> findAllByActive(boolean active);

    Driver findOneById(Integer driverId);

    List<Driver> findAllByActiveTrue();
}
