package com.backend.uberclone.model;

import com.backend.uberclone.dto.FavoriteRouteDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class FavouriteRoute extends Route {


    @Column
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="customer_id")
    private Customer customer;

    public FavouriteRoute(FavoriteRouteDTO favoriteRouteDTO, Customer customer) {
        this.customer = customer;
        this.setRouteGeoJson(favoriteRouteDTO.getRouteGeoJson());
        this.setDistanceInKm(favoriteRouteDTO.getDistance());
        this.setEndCoordinates(new Location(favoriteRouteDTO.getEndCoordinates().get(0), favoriteRouteDTO.getEndCoordinates().get(1)));
        this.setEstimatedTimeInMinutes(favoriteRouteDTO.getEstimatedTime());
        this.setName(favoriteRouteDTO.getName());
        this.setStartCoordinates(new Location(favoriteRouteDTO.getStartCoordinates().get(0), favoriteRouteDTO.getStartCoordinates().get(1)));
        this.setStops(favoriteRouteDTO.getStops());
    }
}
