package com.backend.uberclone.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String start;

    @ElementCollection
    @CollectionTable(name="stops", joinColumns = @JoinColumn(name="route_id"))
    @Column
    private List<String> stops;

    @ElementCollection
    @CollectionTable(name="routeGeoJson", joinColumns = @JoinColumn(name="route_id"))
    @Column(length = 2048)
    private List<String> routeGeoJson;

    @Column
    private double estimatedTimeInMinutes;

    @Column
    private double distanceInKm;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Location startCoordinates;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Location endCoordinates;


    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    public Route(List<String> stops, List<String> routeGeoJson, double estimatedTimeInMinutes, double distanceInKm, List<Double> startCoordinates, List<Double> endCoordinates) {
        this.stops = stops;
        this.routeGeoJson = routeGeoJson;
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
        this.distanceInKm = distanceInKm;
        this.startCoordinates = new Location(startCoordinates.get(1), startCoordinates.get(0));
        this.endCoordinates = new Location(endCoordinates.get(1), endCoordinates.get(0));
    }

}
