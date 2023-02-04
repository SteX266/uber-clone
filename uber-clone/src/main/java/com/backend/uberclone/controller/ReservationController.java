package com.backend.uberclone.controller;

import com.backend.uberclone.dto.*;
import com.backend.uberclone.model.*;
import com.backend.uberclone.service.ReservationService;
import com.backend.uberclone.service.RideService;
import com.backend.uberclone.service.RouteService;
import com.backend.uberclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private RouteService routeService;

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/makeReservation")
    public ResponseEntity<SuccessResponseDTO> makeReservation(@RequestBody ReservationDTO reservationDTO) {
        List<PaymentDTO> paymentDTOS = reservationService.makeReservation(reservationDTO);
        if (paymentDTOS.size() < 1)
            return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.EXPECTATION_FAILED);
        for (PaymentDTO p :
                paymentDTOS) {
            System.out.println("PLACANJEEEEEE");
            System.out.println(p.getCustomerEmail());
            simpMessagingTemplate.convertAndSend("/payment/payment-made", p);
        }
        return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/confirmPayment")
    public ResponseEntity<SuccessResponseDTO> confirmPayment(@RequestBody PaymentDTO paymentDTO) {

        boolean isPaymentDone = this.reservationService.confirmPayment(paymentDTO);
        if (isPaymentDone) {
            Reservation r = reservationService.findOneById(paymentDTO.getReservationId());

            r.setStatus(ReservationStatus.FINISHED);
            if (r.getType() == ReservationType.INSTANT) {
                if (!rideService.makeRide(r)){

                    return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.NOT_FOUND);
                }else {
                    reservationService.initiateChargeUsers(r);
                    return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);
                }
            }
            r.setStatus(ReservationStatus.ASSIGNMENT);
            reservationService.saveReservation(r);
            reservationService.initiateChargeUsers(r);
        }
        return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);
    }




    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/cancelPayment")
    public ResponseEntity<SuccessResponseDTO> cancelPayment(@RequestBody PaymentDTO paymentDTO) {
        this.reservationService.cancelPayment(paymentDTO);
        Reservation r = reservationService.findOneById(paymentDTO.getReservationId());
        for(Payment p:r.getPayments()){
            p.setPaid(false);
            simpMessagingTemplate.convertAndSend("/payment/all-confirmed",new PaymentDTO(p.getAmount(), r.getId(),p.getCustomer().getEmail(),true, -1));
        }
        return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);

    }

    @PostMapping("/addFavoriteRoute")
    public ResponseEntity<SuccessResponseDTO> cancelPayment(@RequestBody FavoriteRouteDTO favoriteRouteDTO) {
        this.routeService.saveFavoriteRoute(favoriteRouteDTO);
        return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);

    }

    @GetMapping("/getReservationByRide/{id}")
    public ResponseEntity<ReservationDTO> getReservationByRide(@PathVariable("id") Integer rideId){
        ReservationDTO reservation = this.reservationService.getReservationByRide(rideId);
        return new ResponseEntity<>(reservation, HttpStatus.OK);

    }


}
