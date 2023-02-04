package com.backend.uberclone.service;

import com.backend.uberclone.dto.RideDTO;
import com.backend.uberclone.model.*;
import com.backend.uberclone.repository.DriverRepository;
import com.backend.uberclone.repository.RideRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class EndRideUnitTests {

    @Mock
    private RideRepository rideRepository;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;
    @InjectMocks
    private RideService rideService;

    private Ride rideWithCustomers;
    private Ride rideNull;
    private RideDTO dto;

    @BeforeEach
    public void setRides() {
        Ride r = new Ride();
        HashSet<Customer> customers = new HashSet<>();
        customers.add(new Customer());
        customers.add(new Customer());
        r.setReservation(new Reservation());
        r.getReservation().setCustomers(customers);
        r.setStartTime(LocalDateTime.now().minusHours(1));
        r.setDriver(new Driver());
        r.setId(1);
        rideWithCustomers = r;
        rideNull = new Ride();
        dto = new RideDTO(1, 1);
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void rideEndValueStatus() {
        rideWithCustomers.endRide();
        assertEquals(rideWithCustomers.getStatus(), RideStatus.FINISHED);
    }

    @Test
    public void customersNotInRide() {
        rideWithCustomers.endRide();
        for (Customer c : rideWithCustomers.getReservation().getCustomers()) {
            assertFalse(c.isRiding());
        }
    }

    @Test
    public void noCustomersOnRide() {
        assertThrows(NullPointerException.class, rideNull::endRide);
    }

    @Test
    public void timeSet() {
        rideWithCustomers.endRide();
        int isAfter = rideWithCustomers.getEndTime().compareTo(rideWithCustomers.getStartTime());
        assertTrue(isAfter > 0);
    }

    @Test
    public void noRideWithDriver() {

        when(rideRepository.findByIdAndStatusAndDriverId(1, RideStatus.ONGOING, 1)).thenReturn(null);
        assertNull(rideService.endRide(dto));
    }

    @Test
    public void invalidDTO() {

        when(rideRepository.findByIdAndStatusAndDriverId(null, RideStatus.ONGOING, null)).thenReturn(null);
        assertNull(rideService.endRide(dto));
    }

    @Test
    public void driverHasNoRidesList() {
        Driver d = new Driver();
        d.setId(1);
        when(driverRepository.findOneById(1)).thenReturn(d);
        when(rideRepository.findByIdAndStatusAndDriverId(1, RideStatus.ONGOING, 1)).thenReturn(rideWithCustomers);
        assertThrows(NullPointerException.class, () -> rideService.endRide(dto));
    }


    @Test
    public void successfullEnd() {
        Driver d = new Driver();
        d.setId(1);
        d.setRides(new ArrayList<>());
        rideWithCustomers.setDriver(d);
        when(driverRepository.findOneById(1)).thenReturn(d);
        when(rideRepository.findByIdAndStatusAndDriverId(1, RideStatus.ONGOING, 1)).thenReturn(rideWithCustomers);
        when(rideRepository.save(rideWithCustomers)).thenReturn(rideWithCustomers);
        Ride r = rideService.endRide(dto);
        assertEquals(r.getStatus(), RideStatus.FINISHED);
        assertNotNull(r.getEndTime());
        assertTrue(r.getDriver().isAvailable());
    }


}
