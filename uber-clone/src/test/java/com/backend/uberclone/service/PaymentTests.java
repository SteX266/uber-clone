package com.backend.uberclone.service;

import com.backend.uberclone.dto.PaymentDTO;
import com.backend.uberclone.model.Customer;
import com.backend.uberclone.model.Payment;
import com.backend.uberclone.model.Reservation;
import com.backend.uberclone.model.ReservationStatus;
import com.backend.uberclone.repository.CustomerRepository;
import com.backend.uberclone.repository.PaymentRepository;
import com.backend.uberclone.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PaymentTests {

    @Mock
    private ReservationRepository reservationRepository;


    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    SimpMessagingTemplate simpMessagingTemplate;

    @InjectMocks
    private ReservationService reservationService;

    PaymentDTO dto;

    Reservation reservation;

    Customer customer;

    ArrayList<Payment> payments;

    Customer customer2;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
         dto = new PaymentDTO(100,1,"serfezev@gmail.com",false,1);
         reservation = new Reservation();
         reservation.setStatus(ReservationStatus.PAYMENT);
         customer = new Customer();
         customer.setEmail("serfezev@gmail.com");
         customer.setCoins(200);
         customer2 = new Customer();
         customer2.setEmail("zoran@gmail");
         customer2.setCoins(200);
         payments = new ArrayList<>();
         payments.add(new Payment(customer, 100));
         payments.add(new Payment(customer2, 100));
         reservation.setPayments(payments);
    }

    @Test
    public void reservationNotFound(){

        when(reservationRepository.findOneById(1)).thenReturn(null);
        when(customerRepository.findOneByEmail("serfezev@gmail.com")).thenReturn(customer);
        assertFalse(reservationService.confirmPayment(dto));


    }

    @Test
    public void customerNotFound(){

        when(reservationRepository.findOneById(1)).thenReturn(reservation);
        when(customerRepository.findOneByEmail("serfezev@gmail.com")).thenReturn(null);
        assertFalse(reservationService.confirmPayment(dto));
    }

    @Test
    public void notAllPayed(){
        payments.get(0).setPaid(false);
        when(reservationRepository.findOneById(1)).thenReturn(reservation);
        when(customerRepository.findOneByEmail("serfezev@gmail.com")).thenReturn(customer);
        assertFalse(reservationService.confirmPayment(dto));
        assertTrue(payments.get(0).isPaid());

    }

    @Test
    public void allPayed(){
        payments.get(0).setPaid(false);
        payments.get(1).setPaid(true);
        when(reservationRepository.findOneById(1)).thenReturn(reservation);
        when(customerRepository.findOneByEmail("serfezev@gmail.com")).thenReturn(customer);
        assertTrue(reservationService.confirmPayment(dto));
        assertTrue(payments.get(0).isPaid());
    }

    @Test
    public void noEnoughMoney(){
        payments.get(1).setPaid(true);
        customer.setCoins(0);
        when(reservationRepository.findOneById(1)).thenReturn(reservation);
        when(customerRepository.findOneByEmail("serfezev@gmail.com")).thenReturn(customer);
        assertFalse(reservationService.confirmPayment(dto));
        assertEquals(reservation.getStatus(), ReservationStatus.DECLINED);
    }

    @Test
    public void chargeUsersSuccesfully(){
        reservationService.chargeUsers(reservation);
        assertEquals(customer.getCoins(),100);
        assertEquals(customer2.getCoins(),100);
    }

    @Test
    public void cancelPayment(){
        when(reservationRepository.findOneById(1)).thenReturn(reservation);
        reservationService.cancelPayment(dto);
        assertEquals(reservation.getStatus(), ReservationStatus.DECLINED);

    }

}
