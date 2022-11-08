package com.backend.uberclone.repository;

import com.backend.uberclone.model.Ride;
import com.backend.uberclone.model.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride,Integer> {
    Ride findByIdAndStatusIn(Integer id, List<RideStatus> statuses);
    Ride findByIdAndStatus(Integer id, RideStatus status);

    Ride findOneById(int rideId);

}
