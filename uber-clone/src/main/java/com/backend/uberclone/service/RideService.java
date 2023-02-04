package com.backend.uberclone.service;

import com.backend.uberclone.dto.*;
import com.backend.uberclone.model.*;
import com.backend.uberclone.repository.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// treba videti kako da pretplatim neki servis na promene repozitorijuma ili tako nesto da mogu da vrsim real time promene unutar sistema
// treba videti kako da se propagiraju promene korisnika na front da je aktivan ili da je neaktivan itd. mozda se za to koriste web socketi ali nisam siguran

@Service
public class RideService {

    private RideRepository rideRepository;
    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    public void setRideRepository(RideRepository rideRepository) { this.rideRepository = rideRepository; }


    public Ride rejectRide(@NotNull RejectionDTO rejectionDTO) {
        Driver driver = driverRepository.findOneById(rejectionDTO.getDriverId());
        Ride ride = rideRepository.findByIdAndDriverId(rejectionDTO.getRideId(), driver.getId());
        if(ride == null) return null;
        ride.setRejection(rejectionDTO.createRejection(ride));
        if(ride.getStatus() == RideStatus.ARRIVING || ride.getStatus() == RideStatus.ARRIVED) {
            simpMessagingTemplate.convertAndSend("/ride/rejected", new RideDTO(ride.getId(), ride.getDriver().getId()));
        }
        if(ride.getStatus() == RideStatus.ONGOING){
            simpMessagingTemplate.convertAndSend("/ride/aborted", new RideDTO(ride.getId(), ride.getDriver().getId()));
        }
        ride.cancelRide();
        setUpDriver(driver);
        refundClients(ride);
        return rideRepository.save(ride);
    }

    public void refundClients(Ride ride) {
        for (Customer c:ride.getCustomers()){
            c.addCoins(ride.getReservation().getPayments().get(0).getAmount());
            customerRepository.save(c);
        }

    }

    public void setUpDriver(Driver driver) {
        Ride ride = driver.getNextRide();
        if (ride == null){
            driver.setAvailable(true);
            driverRepository.save(driver);
        }
        else{
            simpMessagingTemplate.convertAndSend("/ride/new-ride", new DriverNewRideNotificationDTO(ride.getId(),ride.getDriver().getEmail()));
        }
    }

    public Ride startRide(RideDTO rideDTO) {
        Ride ride = rideRepository.findByIdAndStatusAndDriverId(rideDTO.getRideId(), RideStatus.ARRIVING, rideDTO.getDriverId());
        if(ride == null) return null;
        ride.startRide();
        return rideRepository.save(ride);
    }

    public Ride endRide(RideDTO rideDTO) {
        Driver driver = driverRepository.findOneById(rideDTO.getDriverId());
        if (driver == null)return null;
        Ride ride = rideRepository.findByIdAndStatusAndDriverId(rideDTO.getRideId(), RideStatus.ONGOING, driver.getId());
        if(ride == null) return null;
        ride.endRide();
        setUpDriver(driver);
        Ride saved = rideRepository.save(ride);
        simpMessagingTemplate.convertAndSend("/ride/finished", new RideDTO(ride.getId(), ride.getDriver().getId()));
        return saved;
    }


    public Ride createRide(Reservation r) {
        Driver d = findClosestAvailableDriver(r);
        if (d == null){
            return null;
        }
        Ride ride = new Ride();
        ride.setDriver(d);
        ride.setReservation(r);
        ride.setStatus(RideStatus.ARRIVING);
        ride.setEstimatedArrivalTimeInMinutes(5);
        Set<Customer> cus = new HashSet<>();
        for (Customer c: r.getCustomers()) {
            cus.add(c);
        }
        ride.setCustomers(cus);
        for(Customer c:ride.getCustomers()){
            customerRepository.save(c);
        }
        Ride rajd = rideRepository.save(ride);
        r.setRide(rajd);

        reservationRepository.save(r);
        return rajd;
    }

    public boolean makeRide(Reservation r) {
        Ride newRide = this.createRide(r);
        if (newRide== null) {
            for (Payment p : r.getPayments()) {
                simpMessagingTemplate.convertAndSend("/payment/all-confirmed", new PaymentDTO(p.getAmount(), r.getId(), p.getCustomer().getEmail(), true, -1));
            }
            r.setStatus(ReservationStatus.DECLINED);
            reservationRepository.save(r);
            return false;
        }

        if (newRide.getDriver().isAvailable()) {
            simpMessagingTemplate.convertAndSend("/ride/new-ride", new DriverNewRideNotificationDTO(newRide.getId(), newRide.getDriver().getEmail()));
            this.userService.setDriverAvailable(newRide.getDriver(), false);
        }
        return true;
    }

    private Driver findClosestAvailableDriver(Reservation r) {

        Location startCoordinates = r.getRoute().getStartCoordinates();
        List<Driver> allDrivers = driverRepository.findAllByActiveTrue();

        double minDistance = 500000;
        Driver closestDriver = null;

        for (Driver d:allDrivers){
            if(!isDriverSuitable(d,r)){
                continue;
            }
            int numberOfRides = 0;
            double distance = 0;

            if(d.isAvailable()){
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

    private boolean isDriverSuitable(Driver d, Reservation r) {
        Vehicle v = d.getVehicle();
        if(r.isHasPet() && !v.isAllowsPet()){
            return false;
        }
        if(r.isHasBaby() && !v.isAllowsBaby()){
            return false;
        }
        if(r.getVehicleType() != VehicleType.ANY && r.getVehicleType() != v.getType()){
            return false;
        }
        return !d.isDriverOverworked();


    }

    public List<String> getGeoJsonRoute(Integer rideId) {
        Ride ride = rideRepository.findOneById(rideId);
        return ride.getReservation().getRoute().getRouteGeoJson();
    }

    public List<RideHistoryDTO> getRideHistory(Integer userId) {
        User u = this.userRepository.findOneById(userId);
        List<RideHistoryDTO> rideHistory =  new ArrayList<>();
        List<Ride> rides;
        if(u.getRole().equals("CLIENT")){
            rides = new ArrayList<>();
            Customer c = this.customerRepository.findOneById(userId);
            for(Reservation r:c.getReservations()){
                if(r.getRide() != null){
                    rides.add(r.getRide());
                }
            }
        }
        else if(u.getRole().equals("DRIVER")){
            Driver d = this.driverRepository.findOneById(userId);
            rides = d.getRides();
        }
        else{
            rides = rideRepository.findAll();
        }
        for (Ride r:rides){
            rideHistory.add(new RideHistoryDTO(r));
        }
        return rideHistory;
    }

    public void setRideArrived(RideDTO rideDTO) {
        Ride r = rideRepository.findOneById(rideDTO.getRideId());
        r.setStatus(RideStatus.ARRIVED);
        rideRepository.save(r);

    }

}

