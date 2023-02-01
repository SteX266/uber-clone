package com.backend.uberclone.dto;

import com.backend.uberclone.model.ReservationType;
import com.backend.uberclone.model.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    List<String> stops;

    String reservationTime;

    List<String> routeGeoJson;

    List<String> customers;

    VehicleType vehicleType;

    ReservationType reservationType;

    boolean hasBaby;

    boolean hasPet;

    double distance;

    double estimatedTime;

    double estimatedCost;

    List<Double> startCoordinates;

    List<Double> endCoordinates;



}
