package com.backend.uberclone.controller;


import com.backend.uberclone.dto.CoinDTO;
import com.backend.uberclone.dto.SuccessResponseDTO;
import com.backend.uberclone.dto.UserDTO;
import com.backend.uberclone.service.DriverService;
import com.backend.uberclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/driver", produces = MediaType.APPLICATION_JSON_VALUE)
public class DriverController {

    @Autowired
    private DriverService driverService;
    @PreAuthorize("hasRole('DRIVER')")

    @PostMapping(value="/logoutDriver")
    public ResponseEntity<SuccessResponseDTO> logoutDriver(@RequestBody UserDTO userDTO){
        this.driverService.logoutDriver(userDTO.getId());
        return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);
    }
}
