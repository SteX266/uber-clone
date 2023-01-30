package com.backend.uberclone.controller;

import com.backend.uberclone.dto.UserDTO;
import com.backend.uberclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    @Autowired
    private UserService userService;


    @GetMapping(value = "/getCustomerCoinAmount/{id}")
    public ResponseEntity<Double> getProfileInfo(@PathVariable("id") Integer id){
        return new ResponseEntity<>(userService.getCustomerCoinAmount(id), HttpStatus.OK);
    }
}
