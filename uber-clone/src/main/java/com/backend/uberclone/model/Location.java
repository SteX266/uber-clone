package com.backend.uberclone.model;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(mappedBy = "currentLocation")
    private Driver driver;

    @Column
    private Double latitude;
    @Column
    private Double longitude;

    public Location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double calculateDistance(Location location){
        double distance = sqrt(Math.pow(this.latitude - location.getLatitude(),2) + Math.pow(this.longitude - location.getLongitude(),2));

        return distance;
    }

    public List<Double> toList() {
        List<Double> list = new ArrayList<>();
        list.add(latitude);
        list.add(longitude);
        return list;
    }
}
