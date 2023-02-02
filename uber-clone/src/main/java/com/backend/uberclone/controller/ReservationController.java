package com.backend.uberclone.controller;

import com.backend.uberclone.dto.DriverNewRideNotificationDTO;
import com.backend.uberclone.dto.PaymentDTO;
import com.backend.uberclone.dto.ReservationDTO;
import com.backend.uberclone.dto.SuccessResponseDTO;
import com.backend.uberclone.model.*;
import com.backend.uberclone.service.ReservationService;
import com.backend.uberclone.service.RideService;
import com.backend.uberclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/reservation", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RideService rideService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserService userService;


    @PostMapping("/makeReservation")
    public ResponseEntity<SuccessResponseDTO> makeReservation(@RequestBody ReservationDTO reservationDTO) {
        List<PaymentDTO> paymentDTOS = reservationService.makeReservation(reservationDTO);
        if (paymentDTOS.size() < 1)
            return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.EXPECTATION_FAILED);
        for (PaymentDTO p :
                paymentDTOS) {
            simpMessagingTemplate.convertAndSend("/payment/payment-made", p);
        }
        return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);
    }


    @PostMapping("/confirmPayment")
    public ResponseEntity<SuccessResponseDTO> confirmPayment(@RequestBody PaymentDTO paymentDTO) {

        boolean isPaymentDone = this.reservationService.confirmPayment(paymentDTO);
        if (isPaymentDone) {
            Reservation r = reservationService.findOneById(paymentDTO.getReservationId());
            System.out.println("KerinKurac");
            for (Customer c: r.getCustomers()
                 ) {
                System.out.println(c.getUsername());
            }
            r.setStatus(ReservationStatus.FINISHED);
            if (r.getType() == ReservationType.INSTANT) {
                if (!makeRide(r)){
                    return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.NOT_FOUND);
                }else {
                    chargeUsers(r);
                    return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);
                }
            }
            chargeUsers(r);
        }
        return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);
    }

    public boolean makeRide(Reservation r) {
        Ride newRide = rideService.createRide(r);
        if (newRide== null) {
            for (Payment p : r.getPayments()) {
                simpMessagingTemplate.convertAndSend("/payment/all-confirmed", new PaymentDTO(p.getAmount(), r.getId(), p.getCustomer().getEmail(), true));
            }
            return false;
        }

        if (newRide.getDriver().isAvailable()) {
            simpMessagingTemplate.convertAndSend("/ride/new-ride", new DriverNewRideNotificationDTO(newRide.getId(), newRide.getDriver().getEmail()));
            this.userService.setDriverAvailable(newRide.getDriver(), false);
        }
        return true;
    }

    private  void chargeUsers(Reservation r){
        reservationService.chargeUsers(r);
        for (Payment p : r.getPayments()) {
         simpMessagingTemplate.convertAndSend("/payment/all-confirmed", new PaymentDTO(p.getAmount(), r.getId(), p.getCustomer().getEmail(), false));
     }
    }
    @PostMapping("/cancelPayment")
    public ResponseEntity<SuccessResponseDTO> cancelPayment(@RequestBody PaymentDTO paymentDTO) {
        this.reservationService.cancelPayment(paymentDTO);
        Reservation r = reservationService.findOneById(paymentDTO.getReservationId());
        for(Payment p:r.getPayments()){
            p.setPaid(false);
            simpMessagingTemplate.convertAndSend("/payment/all-confirmed",new PaymentDTO(p.getAmount(), r.getId(),p.getCustomer().getEmail(),true));
        }
        return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);

    }


}
