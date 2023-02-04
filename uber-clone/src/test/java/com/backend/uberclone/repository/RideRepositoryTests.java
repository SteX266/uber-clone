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
    private RideRepository rideRepository;

   @Test
    public void findByIdDriverId(){

       Ride ride = this.rideRepository.findByIdAndDriverId(1,3);
       assertNotNull(ride);

       assertEquals(ride.getStatus() , RideStatus.FINISHED);

   }
   @Test
    public void saveRide(){
       Ride ride = new Ride();
       ride.setStatus(RideStatus.CANCELED);
       Ride newRide = rideRepository.save(ride);

       assertNotNull(newRide);
       assertEquals(RideStatus.CANCELED,newRide.getStatus());
   }

   @Test
    public void findByIdAndStatusAndDriverId(){
       Ride ride = this.rideRepository.findByIdAndStatusAndDriverId(1,RideStatus.FINISHED,3);
       assertNotNull(ride);
   }
}
