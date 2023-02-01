package com.backend.uberclone.controller;


import com.backend.uberclone.dto.RejectionDTO;
import com.backend.uberclone.dto.RideDTO;
import com.backend.uberclone.model.Driver;
import com.backend.uberclone.model.Ride;
import com.backend.uberclone.service.RideService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public void setRideService(RideService rideService) { this.rideService = rideService; }

    @PostMapping("/rejectRide")
    public ResponseEntity<String> rejectRide(@RequestBody  RejectionDTO rejectionDTO) {
        rideService.rejectRide(rejectionDTO); // treba vozac da se izvuce iz tokena
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/startRide")
    public ResponseEntity<String> startRide(@RequestBody RideDTO rideDTO) {
        rideService.startRide(rideDTO);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/endRide")
    public ResponseEntity<String> endRide(@RequestBody RideDTO rideDTO) {
        rideService.endRide(rideDTO);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("/getGeoJsonRoute/{id}")
    public ResponseEntity<List<String>> abortRide(@PathVariable("id") String rideId) {
        List<String> geoJsonRoute = rideService.getGeoJsonRoute(Integer.valueOf(rideId));
        return new ResponseEntity<>(geoJsonRoute, HttpStatus.OK);
    }



}
