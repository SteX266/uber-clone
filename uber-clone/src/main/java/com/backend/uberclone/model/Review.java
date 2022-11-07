package com.backend.uberclone.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.CharArrayReader;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ride_id")
    private Ride ride;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="reviewer_id")
    private Customer reviewer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="recipient_id")
    private Driver recipient;


    @Column
    private int carRating;
    @Column
    private int driverRating;
    @Column
    private String comment;

    public Review(Ride ride, Customer reviewer, Driver recipient, int carRating, int driverRating, String comment){

        this.ride = ride;
        this.reviewer = reviewer;
        this.recipient = recipient;
        this.carRating = carRating;
        this.driverRating = driverRating;
        this.comment = comment;
    }

}
