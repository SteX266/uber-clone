package com.backend.uberclone.service;

import com.backend.uberclone.model.Driver;
import com.backend.uberclone.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public Driver findOneById(Integer id){
        return this.driverRepository.findOneById(id);
    }
}
