package com.backend.uberclone.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    private int numberOfRides;
    private double numberOfKilometers;
    private double money;
    private int userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String userRole;

    public ReportDTO(int numberOfRides, double numberOfKilometers, double money){
        this.numberOfKilometers = numberOfKilometers;
        this.numberOfRides = numberOfRides;
        this.money = money;
    }

}
