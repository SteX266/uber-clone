package com.backend.uberclone.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String model;

    @Column
    private int numberOfSeats;

    @Column
    private boolean allowsPet;

    @Column
    private boolean allowsBaby;

    @OneToOne(mappedBy = "vehicle")
    private Driver driver;

    @Column
    @Enumerated(EnumType.STRING)
    private VehicleType type;
}
