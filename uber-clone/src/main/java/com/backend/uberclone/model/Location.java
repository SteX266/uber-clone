package com.backend.uberclone.model;

import lombok.*;

import javax.persistence.*;

import static java.lang.Math.sqrt;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

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
}
