package com.backend.uberclone.repository;

import com.backend.uberclone.model.Reservation;
import com.backend.uberclone.model.ReservationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Reservation findOneById(Integer id);

    List<Reservation> findAllByType(ReservationType scheduled);
}
