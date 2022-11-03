package com.backend.uberclone.controller;

import com.backend.uberclone.dto.ReviewDTO;
import com.backend.uberclone.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"}, allowedHeaders = "*")
@RequestMapping(value = "/review", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReviewController {


    @Autowired
    private ReviewService reviewService;

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping(value = "/createReview")
    public ResponseEntity<String> createReview(@RequestParam int reservationId, @RequestParam String username, @RequestParam String text, @RequestParam int rating){
        boolean isSuccessful = reviewService.createReview(reservationId, username, text, rating);

        if (isSuccessful){
            return new ResponseEntity<>("OK", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Already reviewed!", HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/getAllReviws")
    public ResponseEntity<ArrayList<ReviewDTO>> getAllReviws(){
        return new ResponseEntity<>(reviewService.getAllReviws(), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/acceptReviw")
    public boolean acceptReviw(@RequestBody ReviewDTO dto){
        return reviewService.acceptReviw(dto);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/declineReviw")
    public boolean declineReviw(@RequestBody ReviewDTO dto){
        return reviewService.declineReviw(dto);
    }
}
