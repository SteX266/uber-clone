package com.backend.uberclone.repository;

import com.backend.uberclone.model.Ride;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideCrudRepository extends CrudRepository<Ride, Long> {
}

