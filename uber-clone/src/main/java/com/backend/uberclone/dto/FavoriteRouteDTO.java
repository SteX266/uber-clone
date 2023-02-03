package com.backend.uberclone.dto;

import com.backend.uberclone.model.FavouriteRoute;
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

    public FavoriteRouteDTO(FavouriteRoute favouriteRoute, String customer) {
        this.name = favouriteRoute.getName();
        this.stops = favouriteRoute.getStops();
        this.routeGeoJson = favouriteRoute.getRouteGeoJson();
        this.estimatedTime = favouriteRoute.getEstimatedTimeInMinutes();
        this.distance = favouriteRoute.getDistanceInKm();
        this.startCoordinates = favouriteRoute.getStartCoordinates().toList();
        this.endCoordinates = favouriteRoute.getEndCoordinates().toList();
        this.customer = customer;
    }

}
