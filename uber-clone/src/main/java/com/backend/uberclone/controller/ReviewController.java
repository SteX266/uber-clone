package com.backend.uberclone.controller;

import com.backend.uberclone.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/review", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReviewController {


    @Autowired
    private ReviewService reviewService;

    @GetMapping(value = "/createReview")
    public ResponseEntity<String> createReview(@RequestParam int rideId, @RequestParam String customer, @RequestParam String comment, @RequestParam int carRating, @RequestParam int driverRating){
        boolean isSuccessful = reviewService.createReview(rideId, customer, comment, carRating, driverRating);

        if (isSuccessful){
            return new ResponseEntity<>("OK", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Already reviewed!", HttpStatus.FORBIDDEN);
        }
    }

}

