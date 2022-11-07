package com.backend.uberclone.service;


import com.backend.uberclone.model.Customer;
import com.backend.uberclone.model.Review;
import com.backend.uberclone.model.Ride;
import com.backend.uberclone.repository.CustomerRepository;
import com.backend.uberclone.repository.ReviewRepository;
import com.backend.uberclone.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReviewService {


    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RideRepository rideRepository;




    public boolean createReview(int rideId, String customerEmail, String comment, int carRating, int driverRating) {
        Customer reviewer = customerRepository.findOneByEmail(customerEmail);
        Ride ride = rideRepository.findOneById(rideId);
        if (isRatingValid(carRating, driverRating) && isRideDateValid(ride.getStartTime())){

            Review review = new Review(ride, reviewer, ride.getDriver(), carRating, driverRating, comment);
            reviewRepository.save(review);
            return true;

        }
        return false;
    }

    private boolean isRideDateValid(LocalDateTime startTime) {
        LocalDateTime today = LocalDateTime.now();
        if (today.minusDays(3).isAfter(startTime)){
            return false;
        }
        return true;


    }

    private boolean isRatingValid(int carRating, int driverRating) {
        return carRating >= 0 && carRating <= 5 && driverRating >= 0 && driverRating <= 5;
    }


}
