package com.backend.uberclone.controller;


import com.backend.uberclone.dto.LocationDTO;
import com.backend.uberclone.dto.PriceRangeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/price", produces = MediaType.APPLICATION_JSON_VALUE)
public class PriceController {

    final Double PREMIUM_COEFFICIENT = 200.;

    final Double REGULAR_COEFFICIENT = 100.;

    @PostMapping("/calculate-price-range")
    public ResponseEntity<PriceRangeDTO> calculatePrice(@RequestBody Double distance) {
        if(distance < 0) return new ResponseEntity<>(new PriceRangeDTO( 0., 0.),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new PriceRangeDTO(PREMIUM_COEFFICIENT + distance*120, REGULAR_COEFFICIENT + distance*120), HttpStatus.OK);
    }

}
