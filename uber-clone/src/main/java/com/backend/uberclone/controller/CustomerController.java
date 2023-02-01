package com.backend.uberclone.controller;

import com.backend.uberclone.dto.CoinDTO;
import com.backend.uberclone.dto.SuccessResponseDTO;
import com.backend.uberclone.dto.UserDTO;
import com.backend.uberclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value="/addCoins")
    public ResponseEntity<SuccessResponseDTO> addCoins(@RequestBody CoinDTO coins){
        this.userService.addCoins(coins);

        return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);

    }
}
