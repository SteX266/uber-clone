package com.backend.uberclone.repository;

import com.backend.uberclone.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride,Integer> {

    Ride findOneById(int rideId);
}
