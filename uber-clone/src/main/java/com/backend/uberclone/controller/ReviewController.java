package com.backend.uberclone.controller;

import com.backend.uberclone.dto.ReviewDTO;
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

    @RequestMapping("/createReview")
    public ResponseEntity<String> createReview(@RequestBody ReviewDTO reviewDTO){
        boolean isSuccessful = reviewService.createReview(reviewDTO);

        if (isSuccessful){
            return new ResponseEntity<>("OK", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Already reviewed!", HttpStatus.CONFLICT);
        }
    }

}

