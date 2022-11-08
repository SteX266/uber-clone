package com.backend.uberclone.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="route_id")
    private Route route;

    @OneToOne(mappedBy = "reservation")
    @JoinColumn(name="ride_id")
    private Ride ride;

    @ManyToMany(mappedBy = "reservations")
    private Set<Customer> customers;

    @Column
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column
    private boolean hasBaby;

    @Column
    private boolean hasPet;

    @Column
    private LocalDateTime reservationTime;

    @Column
    @Enumerated(EnumType.STRING)
    private ReservationType type;

    @Column
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;


    @Column
    private double estimatedCost;

}
