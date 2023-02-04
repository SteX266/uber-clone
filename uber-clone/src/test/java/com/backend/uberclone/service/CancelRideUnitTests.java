package com.backend.uberclone.service;

import com.backend.uberclone.dto.RejectionDTO;
import com.backend.uberclone.model.*;
import com.backend.uberclone.repository.CustomerRepository;
import com.backend.uberclone.repository.DriverRepository;
import com.backend.uberclone.repository.RideRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.messaging.simp.SimpMessagingTemplate;


import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CancelRideUnitTests {

    @Mock
    private RideRepository rideRepository;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;
    @InjectMocks
    private RideService rideService;

    @Mock
    private CustomerRepository customerRepository;


    private Ride rideWithCustomers;
    private Ride rideNull;

    private RejectionDTO dto;

    private Driver driver;


    @BeforeEach
    public void setRides(){
        Ride r = new Ride();
        HashSet<Customer> customers = new HashSet<>();
        Customer c1 = new Customer();
        c1.setCoins(100);
        Customer c2 = new Customer();
        c2.setCoins(100);
        customers.add(c1);
        customers.add(c2);
        Reservation res= new Reservation();
        ArrayList<Payment> pays = new ArrayList<>();
        Payment payment = new Payment();
        payment.setAmount(100);
        pays.add(payment);
        res.setPayments(pays);
        r.setReservation(res);
        r.getReservation().setCustomers(customers);
        r.setCustomers(customers);
        rideWithCustomers = r;
        rideNull = new Ride();
        dto = new RejectionDTO("Dosta je bilo",1,1);
        driver = new Driver();
        driver.setRides(new ArrayList<>());
        driver.setId(1);
        rideWithCustomers.setDriver(driver);
        MockitoAnnotations.openMocks(this);

    }
    @Test
    public void rideCanceledValueStatus() {
        rideWithCustomers.setStatus(RideStatus.ARRIVING);
        rideWithCustomers.cancelRide();
        assertEquals(rideWithCustomers.getStatus(), RideStatus.CANCELED);
    }
    @Test
    public void rideAbortionValueStatus() {
        rideWithCustomers.setStatus(RideStatus.ONGOING);
        rideWithCustomers.cancelRide();
        assertEquals(rideWithCustomers.getStatus(), RideStatus.ABORTED);
    }



    @Test
    public void customersInRide() {
        rideWithCustomers.cancelRide();
        for (Customer c : rideWithCustomers.getReservation().getCustomers()) {
            assertFalse(c.isRiding());
        }
    }

    @Test
    public void noStatusOnRide(){
        rideWithCustomers.cancelRide();
        assertNull(rideWithCustomers.getStatus());
    }

    @Test
    public void noCustomersOnRide(){

        assertThrows(NullPointerException.class, rideNull::cancelRide);
    }


    @Test
    public void noRideWithDriver(){

        when(driverRepository.findOneById(1)).thenReturn(driver);
        when(rideRepository.findByIdAndDriverId(1,1)).thenReturn(null);
        assertNull(rideService.rejectRide(dto));
    }

    @Test
    public void succefullCancelation(){

        when(driverRepository.findOneById(1)).thenReturn(driver);
        when(rideRepository.findByIdAndDriverId(1,1)).thenReturn(rideWithCustomers);
        when(rideRepository.save(rideWithCustomers)).thenReturn(rideWithCustomers);
        Ride r = rideService.rejectRide(dto);
        assertEquals("Dosta je bilo",r.getRejection().getReason());
        assertTrue(r.getDriver().isAvailable());
        for (Customer c: r.getCustomers()) {
           assertEquals(200,c.getCoins());
        }

    }

    @Test
    public void getDriverNextRide(){
        ArrayList<Ride>r  = new ArrayList<>();
        Ride ride = new Ride();
        ride.setStatus(RideStatus.ARRIVING);
        r.add(ride);
        ride.setDriver(driver);
        driver.setRides(r);
        when(driverRepository.findOneById(1)).thenReturn(driver);
        when(rideRepository.findByIdAndDriverId(1,1)).thenReturn(rideWithCustomers);
        when(rideRepository.save(rideWithCustomers)).thenReturn(rideWithCustomers);
        assertFalse(rideService.rejectRide(dto).getDriver().isAvailable());

    }
}
