package com.backend.uberclone.service;

import com.backend.uberclone.dto.RideDTO;
import com.backend.uberclone.model.Customer;
import com.backend.uberclone.model.Reservation;
import com.backend.uberclone.model.Ride;
import com.backend.uberclone.model.RideStatus;
import com.backend.uberclone.repository.RideRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;

public class StartRideUnitTests {

    @Mock
    private RideRepository rideRepository;

    @InjectMocks
    private RideService rideService;

    private Ride rideWithCustomers;
    private Ride rideNull;

    private RideDTO  dto;

    @BeforeEach
    public void setRides(){
        Ride r = new Ride();
        HashSet<Customer> customers = new HashSet<>();
        customers.add(new Customer());
        customers.add(new Customer());
        r.setReservation(new Reservation());
        r.getReservation().setCustomers(customers);
        rideWithCustomers = r;
        rideNull = new Ride();
        dto = new RideDTO(1,1);

        MockitoAnnotations.openMocks(this);

    }
    @Test
    public void rideStartValueStatus() {
        rideWithCustomers.startRide();
        assertEquals(rideWithCustomers.getStatus(), RideStatus.ONGOING);
    }

    @Test
    public void customersInRide() {
        rideWithCustomers.startRide();
        for (Customer c : rideWithCustomers.getReservation().getCustomers()) {
            assertTrue(c.isRiding());
        }
    }

    @Test
    public void noCustomersOnRide(){
        assertThrows(NullPointerException.class, rideNull::startRide);
    }

    @Test
    public void timeSet(){
        rideWithCustomers.startRide();
        assertNotNull(rideWithCustomers.getStartTime());
        assertNull(rideWithCustomers.getEndTime());
    }

    @Test
    public void noRideWithDriver(){

        when(rideRepository.findByIdAndStatusAndDriverId(1,RideStatus.ARRIVING,1)).thenReturn(null);
        assertNull(rideService.startRide(dto));
    }

    @Test
    public void invalidDTO(){

        when(rideRepository.findByIdAndStatusAndDriverId(null,RideStatus.ARRIVING,null)).thenReturn(null);
        assertNull(rideService.startRide(new RideDTO()));
    }
    @Test
    public void successfullStart() {
        when(rideRepository.findByIdAndDriverId(1,1)).thenReturn(rideWithCustomers);
        when(rideRepository.save(rideWithCustomers)).thenReturn(rideWithCustomers);
        rideWithCustomers.setStatus(RideStatus.ARRIVED);
        Ride r = rideService.startRide(dto);
        assertEquals(r.getStatus(),RideStatus.ONGOING);
        assertNotNull(r.getStartTime());
    }
}
