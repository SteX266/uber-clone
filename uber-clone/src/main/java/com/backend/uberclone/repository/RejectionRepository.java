package com.backend.uberclone.repository;

import com.backend.uberclone.model.Rejection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RejectionRepository extends JpaRepository<Rejection, Integer> {
}
