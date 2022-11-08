package com.backend.uberclone.service;

import com.backend.uberclone.dto.RejectionDTO;
import com.backend.uberclone.model.Driver;
import com.backend.uberclone.model.Ride;
import com.backend.uberclone.model.RideStatus;
import com.backend.uberclone.repository.RideRepository;
import com.backend.uberclone.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

// treba videti kako da pretplatim neki servis na promene repozitorijuma ili tako nesto da mogu da vrsim real time promene unutar sistema
// treba videti kako da se propagiraju promene korisnika na front da je aktivan ili da je neaktivan itd. mozda se za to koriste web socketi ali nisam siguran

@Service
public class RideService {

    RideRepository rideRepository;
    UserRepository<Driver> driverRepository;

//    NotificationService notificationService;
//
//    @Autowired
//    public void setNotificationService(NotificationService notificationService) {
//        this.notificationService = notificationService;
//    }

    @Autowired
    public void setRideRepository(RideRepository rideRepository) { this.rideRepository = rideRepository; }

    @Autowired
    public void setUserRepository(UserRepository<Driver> driverRepository) { this.driverRepository = driverRepository; }

    public void rejectRide(@NotNull RejectionDTO rejectionDTO, @NotNull Driver driver) {
        Ride ride = rideRepository.findByIdAndStatusInAndDriverId(rejectionDTO.getRideId(), Arrays.asList(RideStatus.ARRIVED, RideStatus.ARRIVING), driver.getId());
        if(ride == null) return;
        ride.setRejection(rejectionDTO.createRejection(ride));
        ride.cancelRide();
        rideRepository.save(ride);
    }

    public void startRide(Long rideId, @NotNull Driver driver) {
        Ride ride = rideRepository.findByIdAndStatusAndDriverId(rideId, RideStatus.ARRIVED, driver.getId());
        if(ride == null) return;
        ride.startRide();
        rideRepository.save(ride);
    }

    public void endRide(Long rideId, @NotNull Driver driver) {
        Ride ride = rideRepository.findByIdAndStatusAndDriverId(rideId, RideStatus.ONGOING, driver.getId());
        ride.endRide();
        rideRepository.save(ride);
    }

    public void abortRide(Long rideId, @NotNull Driver driver) {
        Ride ride = rideRepository.findByIdAndStatusAndDriverId(rideId, RideStatus.ONGOING, driver.getId());
        ride.abortRide();
        rideRepository.save(ride);
    }

}
