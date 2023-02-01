package com.backend.uberclone.repository;

import com.backend.uberclone.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}
