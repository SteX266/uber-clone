package com.backend.uberclone.controller;


import com.backend.uberclone.dto.*;
import com.backend.uberclone.model.User;
import com.backend.uberclone.model.Vehicle;
import com.backend.uberclone.service.ProfileService;
import com.backend.uberclone.service.UserService;
import com.backend.uberclone.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
public class VehicleController {


    @Autowired
    private VehicleService vehicleService;

    @GetMapping(value = "/getCarByUserId/{id}")
    public ResponseEntity<CarDTO> getCarByUserId(@PathVariable("id") String id){
        return new ResponseEntity<>(vehicleService.getCarByUserId(Integer.valueOf(id)), HttpStatus.OK);
    }

    @PostMapping("/updateVehicle")
    public ResponseEntity<SuccessResponseDTO> updateVehicle(@RequestBody CarDTO car){

        if (vehicleService.updateVehicle(car)) {
            return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.NOT_FOUND);
        }
    }
}
