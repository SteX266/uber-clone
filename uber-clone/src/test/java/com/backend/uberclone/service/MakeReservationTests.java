package com.backend.uberclone.service;

import com.backend.uberclone.dto.PaymentDTO;
import com.backend.uberclone.dto.ReservationDTO;
import com.backend.uberclone.model.*;
import com.backend.uberclone.repository.CustomerRepository;
import com.backend.uberclone.repository.PaymentRepository;
import com.backend.uberclone.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class MakeReservationTests {


    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private ReservationService reservationService;

    private ReservationDTO dto;

    private Customer c1;
    private Customer c2;

    private HashSet<Customer> twoCustomers;

    private List<Payment> payments;

    @BeforeEach
    public void set() {
        List<String> stops = new ArrayList<>();
        stops.add("puskinova");
        stops.add("gogljeva");

        LocalDateTime reservationTime = LocalDateTime.now();

        List<String> routeGeoJson = new ArrayList<>();
        routeGeoJson.add("");

        List<String> customers = new ArrayList<>();
        customers.add("serfezev@gmail.com");
        customers.add("bubibubisa@gmail.com");

        VehicleType vehicleType = VehicleType.PREMIUM;

        ReservationType reservationType = ReservationType.INSTANT;

        boolean hasBaby = false;

        boolean hasPet = false;

        double distance = 100;

        double estimatedTime = 20;

        double estimatedCost = 200;

        List<Double> startCoordinates = new ArrayList<>();
        startCoordinates.add(2.0);
        startCoordinates.add(2.0);

        List<Double> endCoordinates = new ArrayList<>();
        endCoordinates.add(2.0);
        endCoordinates.add(2.0);
        dto = new ReservationDTO(stops, reservationTime, routeGeoJson, customers, vehicleType, reservationType, hasBaby, hasPet, distance, estimatedTime, estimatedCost, startCoordinates, endCoordinates);


        MockitoAnnotations.openMocks(this);
        c1 = new Customer();
        c1.setEmail("serfezev@gmail.com");
        c2 = new Customer();
        c2.setEmail("bubibubisa@gmail.com");
        HashSet<Reservation> reservations = new HashSet<>();
        Reservation r1 = new Reservation();
        Reservation r2 = new Reservation();
        reservations.add(r1);
        reservations.add(r2);
        c1.setReservations(reservations);
        c2.setReservations(new HashSet<>());
        twoCustomers = new HashSet<>();
        twoCustomers.add(c1);
        twoCustomers.add(c2);
        payments = new ArrayList<>();
        payments.add(new Payment(c1, 100));
        payments.add(new Payment(c2, 100));
        c1.setCoins(200);
        c2.setCoins(200);
    }


    @Test
    public void emptyDTO() {
        assertThrows(NullPointerException.class, () -> reservationService.makeReservation(new ReservationDTO()));
    }


    @Test
    public void noUsers() {
        when(customerRepository.findAllByEmailIn(dto.getCustomers())).thenReturn(new HashSet<>());
        assertThrows(NullPointerException.class, () -> reservationService.makeReservation(new ReservationDTO()));

    }

    @Test
    public void customersHaveOngoingReservations() {

        Reservation r = new Reservation();
        r.setStatus(ReservationStatus.PAYMENT);
        c1.getReservations().add(r);

        when(customerRepository.findAllByEmailIn(dto.getCustomers())).thenReturn(twoCustomers);
        assertEquals(new ArrayList<>(), reservationService.makeReservation(dto));
    }

    @Test
    public void customerNotEnoughMoney() {
        when(customerRepository.findAllByEmailIn(dto.getCustomers())).thenReturn(twoCustomers);
        c1.setCoins(100);
        c2.setCoins(80);
        assertEquals(new ArrayList<>(), reservationService.makeReservation(dto));
    }


    @Test
    public void canPay() {
        when(customerRepository.findAllByEmailIn(dto.getCustomers())).thenReturn(twoCustomers);
        List<PaymentDTO> pays =reservationService.makeReservation(dto);
        for (PaymentDTO p:pays) {
            assertEquals(p.getAmount(),100);
            assertTrue(p.getCustomerEmail().equals("serfezev@gmail.com") || p.getCustomerEmail().equals("bubibubisa@gmail.com"));
            assertFalse(p.isCanceled());
        }
        assertEquals(pays.size(),2);

    }
}