package com.backend.uberclone.controller;

import com.backend.uberclone.dto.PaymentDTO;
import com.backend.uberclone.dto.ReservationDTO;
import com.backend.uberclone.dto.SuccessResponseDTO;
import com.backend.uberclone.service.ReservationService;
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

    ReservationService reservationService;

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


}
