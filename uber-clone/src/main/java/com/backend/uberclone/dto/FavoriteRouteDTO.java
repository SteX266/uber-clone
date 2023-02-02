package com.backend.uberclone.dto;

import com.backend.uberclone.model.Location;
import com.backend.uberclone.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteRouteDTO {

    private String name;

    private String customer;

    private List<String> stops;

    private List<String> routeGeoJson;

    private double estimatedTime;

    private double distance;

    private List<Double> startCoordinates;

    private List<Double> endCoordinates;

}
