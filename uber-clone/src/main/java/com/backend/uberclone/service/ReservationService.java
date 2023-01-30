package com.backend.uberclone.service;

import com.backend.uberclone.dto.PaymentDTO;
import com.backend.uberclone.dto.ReservationDTO;
import com.backend.uberclone.model.*;
import com.backend.uberclone.repository.CustomerRepository;
import com.backend.uberclone.repository.PaymentRepository;
import com.backend.uberclone.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ReservationService {

    CustomerRepository customerRepository;

    ReservationRepository reservationRepository;

    @Autowired
    PaymentRepository paymentRepository;


    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Autowired
    public void setReservationRepository(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<PaymentDTO> makeReservation(ReservationDTO reservationDTO) {

        if(reservationDTO.getReservationType().equals(ReservationType.INSTANT)) {
            return makeInstantReservation(reservationDTO);
        }
        return new ArrayList<>();
    }

    private List<PaymentDTO> makeInstantReservation(ReservationDTO reservationDTO) {
        Set<Customer> customers = customerRepository.findAllByEmailIn(reservationDTO.getCustomers());

        if(!checkCustomerBalance(customers, reservationDTO.getEstimatedCost())) return new ArrayList<>();
        List<Payment> payments = createPayments(customers, reservationDTO.getEstimatedCost());
        Reservation reservation = new Reservation(reservationDTO, customers, payments);
        reservation = reservationRepository.save(reservation);

        for (Payment p:payments){
            p.setReservation(reservation);
            this.paymentRepository.save(p);
        }

        return createPaymentDTOS(reservation.getPayments(), reservation.getId());
        // socket.convertAndSend(paymentDto, putanja)
        // sacuva u payment repozitorijum i onda na frontu da se pri loginu dobave svi paymenti
    }

    private boolean checkCustomerBalance(Set<Customer> customers, double totalAmount) {
        double amount = totalAmount / customers.size();
        for (Customer c:
             customers) {
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA");
            System.out.println(c.getCoins());
            if (c.getCoins() < amount) return false;
        }
        return true;
    }

    private List<PaymentDTO> createPaymentDTOS(List<Payment> payments, Integer reservationId) {
        List<PaymentDTO> paymentDTOS = new ArrayList<>();
        for (Payment p: payments) {
            paymentDTOS.add(new PaymentDTO(p.getAmount(), reservationId, p.getCustomer().getEmail(),false));
        }
        return paymentDTOS;
    }


    private List<Payment> createPayments(Set<Customer> customers, double estimatedCost) {
        List<Payment> payments = new ArrayList<>();
        double amount = estimatedCost/ customers.size();
        for (Customer c: customers) {
            payments.add(new Payment(c, amount));
        }
        return payments;
    }


    public boolean confirmPayment(PaymentDTO paymentDTO) {
        Reservation r = reservationRepository.findOneById(paymentDTO.getReservationId());
        Customer c = customerRepository.findOneByEmail(paymentDTO.getCustomerEmail());

        for(Payment p:r.getPayments()){

            if (p.getCustomer().getEmail().equals(c.getEmail())){
                if(c.getCoins() >= paymentDTO.getAmount()){
                    p.setPaid(true);

                }
                else{
                    return false;
                }
            }
        }
        return isPaymentDone(r.getPayments());
    }

    public Reservation findOneById(Integer id){
        return reservationRepository.findOneById(id);
    }
    private boolean isPaymentDone(List<Payment> payments) {
        boolean isPaid = true;
        for(Payment p:payments){
            if(!p.isPaid()){
                isPaid = false;
            }
        }
        System.out.println("IS PAID JEBEM TI SEMEEE");
        System.out.println(isPaid);
        return isPaid;
    }

    public void cancelPayment(PaymentDTO paymentDTO) {
        Reservation r = reservationRepository.findOneById(paymentDTO.getReservationId());
        r.setStatus(ReservationStatus.DECLINED);
    }
}
