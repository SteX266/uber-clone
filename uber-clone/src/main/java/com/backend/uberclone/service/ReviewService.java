package com.backend.uberclone.service;


import com.backend.uberclone.dto.ReviewDTO;
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




    public boolean createReview(ReviewDTO reviewDTO) {
        Customer reviewer = customerRepository.findOneByEmail(reviewDTO.getCustomerEmail());
        Ride ride = rideRepository.findOneById(reviewDTO.getRideId());
        if (ride == null){
            System.out.println("ALOOO9OOOGADSOAOSOGSOASGOGAS");
            return false;
        }
        if (isRatingValid(reviewDTO.getCarRating(), reviewDTO.getDriverRating()) && isRideDateValid(ride.getStartTime())){

            Review review = new Review(ride, reviewer, ride.getDriver(), reviewDTO.getCarRating(), reviewDTO.getDriverRating(), reviewDTO.getComment());
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
