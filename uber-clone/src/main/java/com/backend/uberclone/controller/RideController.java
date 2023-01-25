package com.backend.uberclone.controller;


import com.backend.uberclone.dto.RejectionDTO;
import com.backend.uberclone.model.Driver;
import com.backend.uberclone.service.RideService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"}, allowedHeaders = "*")
@RequestMapping(value = "/ride", produces = MediaType.APPLICATION_JSON_VALUE)
public class RideController {

    RideService rideService;


    @Autowired
    public void setRideService(RideService rideService) { this.rideService = rideService; }

    @PostMapping("/rejectRide")
    public ResponseEntity<String> rejectRide(@RequestBody @NotNull RejectionDTO rejectionDTO) {
        System.out.println(rejectionDTO.getReason());
        if(!rejectionDTO.hasValidFields()) return new ResponseEntity<>("Invalid data", HttpStatus.BAD_REQUEST);
        rideService.rejectRide(rejectionDTO, new Driver()); // treba vozac da se izvuce iz tokena
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/startRide")
    public ResponseEntity<String> startRide(@RequestParam Long rideId, @RequestParam Integer driverId) {
        rideService.startRide(rideId, driverId);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/endRide")
    public ResponseEntity<String> endRide(@RequestParam Long rideId) {
        if(rideId < 0) return new ResponseEntity<>("Invalid data", HttpStatus.BAD_REQUEST);
        rideService.endRide(rideId, new Driver());
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/abortRide")
    public ResponseEntity<String> abortRide(@RequestParam Long rideId) {
        if(rideId < 0) return new ResponseEntity<>("Invalid data", HttpStatus.BAD_REQUEST);
        rideService.abortRide(rideId, new Driver());
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }


}
