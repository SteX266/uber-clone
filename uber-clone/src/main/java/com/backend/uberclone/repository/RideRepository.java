package com.backend.uberclone.repository;

import com.backend.uberclone.model.Driver;
import com.backend.uberclone.model.Ride;
import com.backend.uberclone.model.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface RideRepository extends JpaRepository<Ride,Integer> {
    Ride findByIdAndStatusAndDriverId(Integer id, RideStatus status, Integer driver_id);

    ArrayList<Ride> findRidesByDriver(Driver d);

    Ride findOneById(int rideId);

    Ride findByIdAndDriverId(Integer rideId, Integer id);
}
