package com.backend.uberclone.repository;

import com.backend.uberclone.model.Driver;
import com.backend.uberclone.model.Ride;
import com.backend.uberclone.model.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RideRepository extends JpaRepository<Ride,Integer> {
    Ride findByIdAndStatusIn(Long id, Collection<RideStatus> status);
    Ride findByIdAndStatus(Long id, RideStatus status);
    Ride findByIdAndStatusAndDriverId(Long id, RideStatus status, Long driver_id);
    Ride findByIdAndStatusInAndDriverId(Long id, Collection<RideStatus> status, Long driverId);
}
