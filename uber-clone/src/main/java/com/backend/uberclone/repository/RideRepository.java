package com.backend.uberclone.repository;

import com.backend.uberclone.model.Driver;
import com.backend.uberclone.model.Ride;
import com.backend.uberclone.model.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface RideRepository extends JpaRepository<Ride,Integer> {
    Ride findByIdAndStatusIn(Integer id, Collection<RideStatus> status);
    Ride findByIdAndStatus(Integer id, RideStatus status);
    Ride findByIdAndStatusAndDriverId(Long id, RideStatus status, Integer driver_id);
    Ride findByIdAndStatusInAndDriverId(Long id, Collection<RideStatus> status, Integer driverId);

    ArrayList<Ride> findRidesByDriver(Driver d);

    Ride findOneById(int rideId);
}
