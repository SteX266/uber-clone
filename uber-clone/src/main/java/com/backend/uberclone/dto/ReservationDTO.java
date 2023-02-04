package com.backend.uberclone.dto;

import com.backend.uberclone.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    List<String> stops;

    LocalDateTime reservationTime;

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

    public ReservationDTO(Reservation reservation){
        this.stops = reservation.getRoute().getStops();
        this.reservationTime = LocalDateTime.now();
        this.routeGeoJson = reservation.getRoute().getRouteGeoJson();
        this.customers = new ArrayList<>();
        for (Customer c:reservation.getCustomers()){
            customers.add(c.getUsername());
        }
        this.vehicleType = reservation.getVehicleType();
        this.reservationType = reservation.getType();
        this.hasBaby = reservation.isHasBaby();
        this.hasPet = reservation.isHasPet();
        this.distance = reservation.getRoute().getDistanceInKm();
        this.estimatedTime = reservation.getRoute().getEstimatedTimeInMinutes();
        this.estimatedCost = reservation.getEstimatedCost();
        this.startCoordinates = new ArrayList<>();
        this.startCoordinates.add(reservation.getRoute().getStartCoordinates().getLatitude());
        this.startCoordinates.add(reservation.getRoute().getStartCoordinates().getLongitude());

        this.endCoordinates = new ArrayList<>();
        this.endCoordinates.add(reservation.getRoute().getEndCoordinates().getLatitude());
        this.endCoordinates.add(reservation.getRoute().getEndCoordinates().getLongitude());



    }



}
