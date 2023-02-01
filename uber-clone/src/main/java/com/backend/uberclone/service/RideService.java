package com.backend.uberclone.service;

import com.backend.uberclone.dto.DriverNewRideNotificationDTO;
import com.backend.uberclone.dto.RejectionDTO;
import com.backend.uberclone.dto.RideDTO;
import com.backend.uberclone.model.*;
import com.backend.uberclone.repository.DriverRepository;
import com.backend.uberclone.repository.RideRepository;
import com.backend.uberclone.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// treba videti kako da pretplatim neki servis na promene repozitorijuma ili tako nesto da mogu da vrsim real time promene unutar sistema
// treba videti kako da se propagiraju promene korisnika na front da je aktivan ili da je neaktivan itd. mozda se za to koriste web socketi ali nisam siguran

@Service
public class RideService {

    RideRepository rideRepository;
    @Autowired
    DriverRepository driverRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;



    @Autowired
    public void setRideRepository(RideRepository rideRepository) { this.rideRepository = rideRepository; }


    public void rejectRide(@NotNull RejectionDTO rejectionDTO) {
        Driver driver = driverRepository.findOneById(rejectionDTO.getDriverId());
        Ride ride = rideRepository.findByIdAndDriverId(rejectionDTO.getRideId(), driver.getId());
        if(ride == null) return;
        ride.setRejection(rejectionDTO.createRejection(ride));
        ride.cancelRide();
        setUpDriver(driver);
        rideRepository.save(ride);
    }

    private void setUpDriver(Driver driver) {
        Ride ride = driver.getNextRide();
        if (ride == null){
            driver.setAvailable(true);
            driverRepository.save(driver);
        }
        else{
            simpMessagingTemplate.convertAndSend("/ride/new-ride", new DriverNewRideNotificationDTO(ride.getId(),ride.getDriver().getEmail()));
        }
    }

    public void startRide(RideDTO rideDTO) {
        Ride ride = rideRepository.findByIdAndStatusAndDriverId(rideDTO.getRideId(), RideStatus.ARRIVING, rideDTO.getDriverId());
        if(ride == null) return;
        ride.startRide();
        rideRepository.save(ride);
    }

    public void endRide(RideDTO rideDTO) {
        Driver driver = driverRepository.findOneById(rideDTO.getDriverId());
        Ride ride = rideRepository.findByIdAndStatusAndDriverId(rideDTO.getRideId(), RideStatus.ONGOING, driver.getId());
        ride.endRide();
        setUpDriver(driver);
        rideRepository.save(ride);
    }


    public Ride createRide(Reservation r) {
        Driver d = findClosestAvailableDriver(r.getRoute().getStartCoordinates());
        Ride ride = new Ride();
        ride.setDriver(d);
        ride.setReservation(r);
        ride.setStatus(RideStatus.ARRIVING);
        ride.setEstimatedArrivalTimeInMinutes(5);

        return rideRepository.save(ride);
    }

    private Driver findClosestAvailableDriver(Location startCoordinates) {

        List<Driver> allDrivers = driverRepository.findAllByActiveTrue();

        double minDistance = 500000;
        Driver closestDriver = null;

        for (Driver d:allDrivers){
            int numberOfRides = 0;
            double distance = 0;
            if(d.isAvailable()){
                System.out.println("EJ BREE");
                 distance = d.getCurrentLocation().calculateDistance(startCoordinates);
            }
            else{
                for(Ride ride : rideRepository.findRidesByDriver(d)){
                    if (ride.getStatus() == RideStatus.ARRIVING){
                        numberOfRides++;
                        distance = d.getCurrentLocation().calculateDistance(ride.getReservation().getRoute().getStartCoordinates());
                        distance +=  ride.getReservation().getRoute().getStartCoordinates().calculateDistance(ride.getReservation().getRoute().getEndCoordinates());
                        distance +=  ride.getReservation().getRoute().getEndCoordinates().calculateDistance(startCoordinates);
                    }
                    if (ride.getStatus() == RideStatus.ARRIVED || ride.getStatus() == RideStatus.ONGOING){
                        numberOfRides++;
                        distance = d.getCurrentLocation().calculateDistance(ride.getReservation().getRoute().getEndCoordinates());
                        distance +=    ride.getReservation().getRoute().getEndCoordinates().calculateDistance(startCoordinates);
                    }
                }
            }
            if(numberOfRides > 1){
                continue;
            }
            if(distance < minDistance){
                minDistance = distance;
                closestDriver = d;
            }
        }
        return closestDriver;
    }

    public List<String> getGeoJsonRoute(Integer rideId) {
        Ride ride = rideRepository.findOneById(rideId);
        return ride.getReservation().getRoute().getRouteGeoJson();
    }
}

