package com.backend.uberclone.controller;


import com.backend.uberclone.dto.LocationDTO;
import com.backend.uberclone.dto.SuccessResponseDTO;
import com.backend.uberclone.dto.ToggleAvailableDTO;
import com.backend.uberclone.model.Driver;
import com.backend.uberclone.service.DriverService;
import com.backend.uberclone.service.LocationService;
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
@RequestMapping(value = "/location", produces = MediaType.APPLICATION_JSON_VALUE)
public class LocationController {

    SimpMessagingTemplate simpMessagingTemplate;

    LocationService locationService;

    @Autowired
    DriverService driverService;

    @Autowired
    public void setSimMessagingTemplate(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @PreAuthorize("hasRole('DRIVER')")
    @PostMapping("/startShift")
    public ResponseEntity<String> startShift(@RequestBody LocationDTO locationDTO) {
        System.out.println("USAO U POCETAK SHIFTA");
        if(!locationService.startShift(locationDTO.getDriverId())) return new ResponseEntity<>("Couldn't get driver by id!", HttpStatus.BAD_REQUEST);
        if(!locationService.updateUserLocation(locationDTO)) return new ResponseEntity<>("Couldn't get driver by id!", HttpStatus.BAD_REQUEST);
        simpMessagingTemplate.convertAndSend("/location-updates/new-driver", locationDTO);
        return new ResponseEntity<>("Successfully started shift!", HttpStatus.OK);
    }
    @PreAuthorize("hasRole('DRIVER')")
    @PostMapping("/updateLocation")
    public ResponseEntity<SuccessResponseDTO> updateLocation(@RequestBody LocationDTO locationDTO) {
        System.out.println("UPDATE LOCATION");
        if(!locationService.updateUserLocation(locationDTO)) return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.BAD_REQUEST);
        simpMessagingTemplate.convertAndSend("/location-updates/update-driver-location", locationDTO);
        return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('DRIVER')")
    @PostMapping("/endShift")
    public ResponseEntity<String> endShift(@RequestBody Integer driverId) {
        System.out.println("USAO U POCETAK SHIFTA");
        if(!locationService.endShift(driverId))  return new ResponseEntity<>("Couldn't get driver by id!", HttpStatus.BAD_REQUEST);
        simpMessagingTemplate.convertAndSend("/location-updates/end-drive", driverId);
        return new ResponseEntity<>("Successfully ended shift!", HttpStatus.OK);
    }

    @GetMapping("/getActiveDriverLocations")
    public ResponseEntity<List<LocationDTO>> getActiveDriverLocations() {
        System.out.println("Usao u dobavljanje lokacija svih korisnika");
        return new ResponseEntity<>(locationService.getActiveDriverLocations(), HttpStatus.OK);
    }

    @GetMapping("/getDriverLocationByRideId/{id}")
    public ResponseEntity<LocationDTO> getDriverLocationByRideId(@PathVariable("id") String id) {
        System.out.println("Usao u dobavljanje lokacija jednog korisnika");
        return new ResponseEntity<>(locationService.getDriverLocationByRideId(Integer.valueOf(id)), HttpStatus.OK);
    }

    @PostMapping("/toggleAvailable")
    public ResponseEntity<String> toggleAvailable(@RequestBody ToggleAvailableDTO toggleAvailableDTO) {
        Driver driver = this.driverService.findOneById(toggleAvailableDTO.getDriverId());
        if(driver == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        driverService.setDriverActivity(driver, toggleAvailableDTO.getAvailable());
        return new ResponseEntity<>(HttpStatus.OK);
    }





}
