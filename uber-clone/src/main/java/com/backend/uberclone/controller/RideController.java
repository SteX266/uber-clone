package com.backend.uberclone.controller;


import com.backend.uberclone.dto.*;
import com.backend.uberclone.model.Driver;
import com.backend.uberclone.model.Ride;
import com.backend.uberclone.service.RideService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"}, allowedHeaders = "*")
@RequestMapping(value = "/ride", produces = MediaType.APPLICATION_JSON_VALUE)
public class RideController {

    RideService rideService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @Autowired
    public void setRideService(RideService rideService) { this.rideService = rideService; }

    @MessageMapping("/driverArrived")
    public void sendMessage(RideDTO rideDTO){
        rideService.setRideArrived(rideDTO);
        this.simpMessagingTemplate.convertAndSend("/ride/arrived", rideDTO);
    }

    @PostMapping("/rejectRide")
    public ResponseEntity<SuccessResponseDTO> rejectRide(@RequestBody  RejectionDTO rejectionDTO) {
        rideService.rejectRide(rejectionDTO); // treba vozac da se izvuce iz tokena
        return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);
    }

    @PostMapping("/startRide")
    public ResponseEntity<SuccessResponseDTO> startRide(@RequestBody RideDTO rideDTO) {
        rideService.startRide(rideDTO);
        return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);
    }

    @PostMapping("/endRide")
    public ResponseEntity<SuccessResponseDTO> endRide(@RequestBody RideDTO rideDTO) {
        rideService.endRide(rideDTO);
        return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);
    }

    @GetMapping("/getGeoJsonRoute/{id}")
    public ResponseEntity<List<String>> abortRide(@PathVariable("id") String rideId) {
        List<String> geoJsonRoute = rideService.getGeoJsonRoute(Integer.valueOf(rideId));
        return new ResponseEntity<>(geoJsonRoute, HttpStatus.OK);
    }

    @GetMapping("/getRideHistory/{id}")
    public ResponseEntity<List<RideHistoryDTO>> getRideHistory(@PathVariable("id") Integer userId){
        List<RideHistoryDTO> rides = this.rideService.getRideHistory(userId);
        return new ResponseEntity<>(rides, HttpStatus.OK);

    }


}
