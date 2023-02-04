package com.backend.uberclone.repository;

import com.backend.uberclone.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {
}
