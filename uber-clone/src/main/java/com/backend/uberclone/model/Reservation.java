package com.backend.uberclone.model;


import com.backend.uberclone.dto.ReservationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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


    @OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Payment> payments;

    @Column
    private double estimatedCost;

    public Reservation(ReservationDTO reservationDTO, Set<Customer> customers, List<Payment> payments) {
        this.route = new Route(reservationDTO.getStops(), reservationDTO.getRouteGeoJson(), reservationDTO.getEstimatedTime(), reservationDTO.getDistance());
        this.payments = payments;
        this.customers = customers;
        this.vehicleType = reservationDTO.getVehicleType();
        this.hasBaby = reservationDTO.isHasBaby();
        this.hasPet = reservationDTO.isHasPet();
        this.type = reservationDTO.getReservationType();
        this.status = ReservationStatus.PAYMENT;
        this.estimatedCost = reservationDTO.getEstimatedCost();

    }

}
