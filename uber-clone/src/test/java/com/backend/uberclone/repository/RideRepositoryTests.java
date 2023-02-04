package com.backend.uberclone.repository;

import com.backend.uberclone.model.Ride;
import com.backend.uberclone.model.RideStatus;
import com.backend.uberclone.service.RideService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNull;

public class RideRepositoryTests {
    private RideService rideRepository;

   @Test
    public void findByIdAndStatusAndDriverId(){
       rideRepository.refundClients(new Ride());

   }
}
