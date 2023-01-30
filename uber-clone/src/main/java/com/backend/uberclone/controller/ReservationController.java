package com.backend.uberclone.controller;

import com.backend.uberclone.dto.PaymentDTO;
import com.backend.uberclone.dto.ReservationDTO;
import com.backend.uberclone.dto.SuccessResponseDTO;
import com.backend.uberclone.model.Payment;
import com.backend.uberclone.model.Reservation;
import com.backend.uberclone.model.ReservationStatus;
import com.backend.uberclone.service.ReservationService;
import com.backend.uberclone.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/reservation", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationController {

    ReservationService reservationService;

    @Autowired
    RideService rideService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public void setReservationService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/makeReservation")
    public ResponseEntity<SuccessResponseDTO> makeReservation(@RequestBody ReservationDTO reservationDTO) {
        System.out.println(reservationDTO.getRouteGeoJson().get(0));
        List<PaymentDTO> paymentDTOS = reservationService.makeReservation(reservationDTO);
        if(paymentDTOS.size() < 1) return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.EXPECTATION_FAILED);
        for (PaymentDTO p:
             paymentDTOS) {
            simpMessagingTemplate.convertAndSend("/payment/payment-made",p);
        }
        return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);
    }


    @PostMapping("/confirmPayment")
    public ResponseEntity<SuccessResponseDTO> confirmPayment(@RequestBody PaymentDTO paymentDTO) {
        boolean isPaymentDone = this.reservationService.confirmPayment(paymentDTO);
        if(isPaymentDone){

            Reservation r = reservationService.findOneById(paymentDTO.getReservationId());
            r.setStatus(ReservationStatus.FINISHED);
            boolean driverExists = rideService.createRide(r);
            if (!driverExists){

            }
            for(Payment p:r.getPayments()){
                System.out.println("SALJEM ROCKET");
                simpMessagingTemplate.convertAndSend("/payment/all-confirmed",new PaymentDTO(p.getAmount(), r.getId(),p.getCustomer().getEmail(),false));
            }
        }
        return new ResponseEntity<>(new SuccessResponseDTO(),HttpStatus.OK);
    }

    @PostMapping("/cancelPayment")
    public ResponseEntity<SuccessResponseDTO> cancelPayment(@RequestBody PaymentDTO paymentDTO) {
        this.reservationService.cancelPayment(paymentDTO);
        paymentDTO.setCanceled(true);
        simpMessagingTemplate.convertAndSend("/payment/all-confirmed",paymentDTO);
        return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);

    }


}
