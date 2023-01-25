package com.backend.uberclone.service;

import com.backend.uberclone.dto.LocationDTO;
import com.backend.uberclone.model.Driver;
import com.backend.uberclone.model.Location;
import com.backend.uberclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {


    private UserRepository<Driver> driverRepository;

    @Autowired
    public void setDriverRepository(UserRepository<Driver> driverRepository) {
        this.driverRepository = driverRepository;
    }

    // ovo treba da se nalazi u nekom driver servisu
    public boolean startShift(Integer driverId) {
        Driver driver = driverRepository.findOneById(driverId);
        if(driver == null) return false;
        driver.setActive(true);
        driverRepository.save(driver);
        return true;
    }

    // ovo treba u nekom driver servisu da se nalazi
    public boolean endShift(Integer driverId) {
        Driver driver = driverRepository.findOneById(driverId);
        if(driver == null) return false;
        driver.setActive(false);
        driver.setAvailable(false);
        driverRepository.save(driver);
        return true;
    }
    public boolean updateUserLocation(LocationDTO locationDTO) {
        Driver driver = driverRepository.findOneById(locationDTO.getDriverId());
        if(driver == null) return false;
        if(!driver.isActive()) return false;
        driver.setCurrentLocation(new Location(locationDTO.getLatitude(), locationDTO.getLongitude()));
        driverRepository.save(driver);
        return true;
    }
}
