package com.backend.uberclone.repository;


import com.backend.uberclone.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, Integer> {

    public Review findOneById(int id);
}