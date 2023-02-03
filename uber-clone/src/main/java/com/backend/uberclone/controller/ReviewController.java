package com.backend.uberclone.controller;

import com.backend.uberclone.dto.Able;
import com.backend.uberclone.dto.ReviewDTO;
import com.backend.uberclone.dto.SuccessResponseDTO;
import com.backend.uberclone.model.Review;
import com.backend.uberclone.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/review", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReviewController {


    @Autowired
    private ReviewService reviewService;

    @PreAuthorize("hasRole('CLIENT')")
    @RequestMapping("/createReview")
    public ResponseEntity<SuccessResponseDTO> createReview(@RequestBody ReviewDTO reviewDTO){
        boolean isSuccessful = reviewService.createReview(reviewDTO);

        if (isSuccessful){
            return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.CONFLICT);
        }
    }
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/reviewPossible")
    public ResponseEntity<Able> isReviewPossible(@RequestBody ReviewDTO reviewDTO){

        if(reviewService.canRideBeReviewed(reviewDTO.getRideId(), reviewDTO.getReviewerEmail())){

            return new ResponseEntity<>(new Able(true),HttpStatus.OK);

        }
        return new ResponseEntity<>(new Able(false),HttpStatus.METHOD_NOT_ALLOWED);

    }
}

