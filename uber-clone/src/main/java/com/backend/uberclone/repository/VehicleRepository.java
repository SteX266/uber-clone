package com.backend.uberclone.repository;

import com.backend.uberclone.model.User;
import com.backend.uberclone.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    Vehicle findOneById(int id);
    Vehicle getVehicleByDriverId(int id);

    Vehicle getVehicleByUpdateRequestId(int id);


}
