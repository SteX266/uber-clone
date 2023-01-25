package com.backend.uberclone.controller;


import com.backend.uberclone.dto.LocationDTO;
import com.backend.uberclone.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/location", produces = MediaType.APPLICATION_JSON_VALUE)
public class LocationController {

    SimpMessagingTemplate simpMessagingTemplate;

    LocationService locationService;
    @Autowired
    public void setSimMessagingTemplate(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }


    @PostMapping("/startShift")
    public ResponseEntity<String> startShift(@RequestBody LocationDTO locationDTO) {
        System.out.println("USAO U POCETAK SHIFTA");
        if(!locationService.startShift(locationDTO.getDriverId())) return new ResponseEntity<>("Couldn't get driver by id!", HttpStatus.BAD_REQUEST);
        if(!locationService.updateUserLocation(locationDTO)) return new ResponseEntity<>("Couldn't get driver by id!", HttpStatus.BAD_REQUEST);
        simpMessagingTemplate.convertAndSend("/location-updates/new-driver", locationDTO);
        return new ResponseEntity<>("Successfully started shift!", HttpStatus.OK);

    }

    @PostMapping("/updateLocation")
    public ResponseEntity<String> updateLocation(@RequestBody LocationDTO locationDTO) {
        System.out.println("UPDATE LOCATION");
        if(!locationService.updateUserLocation(locationDTO)) return new ResponseEntity<>("Couldn't get driver by id!", HttpStatus.BAD_REQUEST);
        simpMessagingTemplate.convertAndSend("/location-updates/update-driver-location", locationDTO);
        return new ResponseEntity<>("Successfully updated location!", HttpStatus.OK);
    }

    @PostMapping("/endShift")
    public ResponseEntity<String> endShift(@RequestBody Integer driverId) {
        System.out.println("USAO U POCETAK SHIFTA");
        if(!locationService.endShift(driverId))  return new ResponseEntity<>("Couldn't get driver by id!", HttpStatus.BAD_REQUEST);
        simpMessagingTemplate.convertAndSend("/location-updates/end-drive", driverId);
        return new ResponseEntity<>("Successfully ended shift!", HttpStatus.OK);
    }





}
