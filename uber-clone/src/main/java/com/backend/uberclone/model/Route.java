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
    private Long id;

    @Column
    private String name;

    @Column
    private String start;

    @ElementCollection
    @CollectionTable(name="stops", joinColumns = @JoinColumn(name="route_id"))
    @Column
    private List<String> stops;

    @Column
    private int estimatedTimeInMinutes; // ovo mozda bude promenjeno u zavisnosti od fronta

    @Column
    private double distanceInKm;


    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reservation> reservations;

}
