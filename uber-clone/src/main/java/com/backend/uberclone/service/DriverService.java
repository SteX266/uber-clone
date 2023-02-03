package com.backend.uberclone.service;

import com.backend.uberclone.model.ActivePeriod;
import com.backend.uberclone.model.Driver;
import com.backend.uberclone.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public Driver findOneById(Integer id){
        return this.driverRepository.findOneById(id);
    }

    public void logoutDriver(int id) {
        Driver driver = driverRepository.findOneById(id);
        if (driver == null){
            return;
        }
        this.setDriverActivity(driver, false);
    }

    public void setDriverActivity(Driver driver, boolean isActive){

        if(driver.isActive() == isActive){
            return;
        }
        if(isActive){
            if(driver.isDriverOverworked()){
                return;
            }

            ActivePeriod activePeriod = new ActivePeriod(driver);
            driver.addActivePeriod(activePeriod);
            driver.setActive(true);
            driver.setAvailable(true);
        }
        else{
            driver.setActive(false);
            driver.setAvailable(false);
            driver.getActivePeriods().get(driver.getActivePeriods().size()-1).setEndTime(LocalDateTime.now());

        }
        driverRepository.save(driver);

    }
}
