package com.backend.uberclone.service;


import com.backend.uberclone.dto.ReviewDTO;
import com.backend.uberclone.model.Review;
import com.backend.uberclone.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
public class ReviewService {


    @Autowired
    private ReviewRepository reviewRepository;




    @Transactional
    public boolean createReview(int reservationId, String username, String text, int rating) {

        Reservation reservation = reservationRepository.getLockedReview(reservationId);
        SystemEntity e = reservation.getSystemEntity();
        User u = userRepository.findOneByUsername(username);
        
        for (Review r:e.getReviews()){
            if (r.getClient().getUsername().equals(username)){
                return false;
            }
        }
        Review review = new Review(rating, text, u, reservation.getSystemEntity());
        reservation.getSystemEntity().addReview(review);
        SystemEntity entity = reservation.getSystemEntity();

        double avg = 0;

        for(Review r:entity.getReviews()){
            avg+=r.getScore();
        }

        entity.setAverageScore(avg/entity.getReviews().size());


        reviewRepository.save(review);
        systemEntityRepository.save(entity);
        return true;

    }

    public ArrayList<ReviewDTO> getAllReviews() {
        ArrayList<ReviewDTO> reportDTOs = new ArrayList<>();
        ArrayList<Review> reports = (ArrayList<Review>) reviewRepository.findAll();
        for (Review r: reports) {
            if(!r.isApproved()) {
                reportDTOs.add(new ReviewDTO(r));
            }
        }
        return reportDTOs;
    }
    @Transactional
    public boolean acceptReview(ReviewDTO dto) {
        Review rr = reviewRepository.getLockedReview(dto.getId());
        rr.setApproved(true);
        reviewRepository.save(rr);
        emailService.sendReviewEmail(rr.getSystemEntity().getOwner().getUsername(),rr.getText(),rr.getClient().getUsername(),rr.getScore(),rr.getSystemEntity().getName());
        return true;
    }

    @Transactional
    public boolean declineReview(ReviewDTO dto) {
        Review rr = reviewRepository.getLockedReview(dto.getId());
        reviewRepository.delete(rr);
        emailService.sendReservationReportDeclined(dto.getClient(),dto.getOwner(),rr.getText(),dto.getText());
        return true;
    }
}
