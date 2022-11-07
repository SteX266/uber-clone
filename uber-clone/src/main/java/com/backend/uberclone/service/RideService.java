package com.backend.uberclone.service;

import com.backend.uberclone.dto.RejectionDTO;
import com.backend.uberclone.model.*;
import com.backend.uberclone.repository.RideRepository;
import com.backend.uberclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;


@Service
public class RideService {

    RideRepository rideRepository;
    UserRepository<Driver> driverRepository;

    @Autowired
    public void setRideRepository(RideRepository rideRepository) { this.rideRepository = rideRepository; }

    @Autowired
    public void setUserRepository(UserRepository<Driver> driverRepository) { this.driverRepository = driverRepository; }

    public void rejectRide(RejectionDTO rejectionDTO) {
        // provera korisnika da li je on onaj kome je dodeljena voznja uopste
        // i treba korisnik da bude slobodan ponovo
        Ride ride = rideRepository.findByIdAndStatusIn(rejectionDTO.getRideId(), Arrays.asList(RideStatus.ARRIVED, RideStatus.ARRIVING));
        ride.setRejection(rejectionDTO.createRejection(ride));
        ride.cancel();
        // ovde treba da se stavi korisnik da je available i da se notifikuju korisnici i da se kod njega promeni status
        rideRepository.save(ride);
    }

    public void startRide(Integer rideId, Integer driverId) {
        Ride ride = rideRepository.findByIdAndStatus(rideId, RideStatus.ARRIVED);
        ride.start();
        rideRepository.save(ride);
    }

    public void endRide(Integer rideId, Integer driverId) {
        Ride ride = rideRepository.findByIdAndStatus(rideId, RideStatus.ONGOING);
        ride.end();
        rideRepository.save(ride);
    }

    public void abortRide(Integer rideId, Integer driverId) {
        Ride ride = rideRepository.findByIdAndStatus(rideId, RideStatus.ONGOING);
        ride.abort();
        rideRepository.save(ride);
    }

}
