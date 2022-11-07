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
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="reviewer_id")
    private Customer reviewer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="recipient_id")
    private Driver recipient;


    @Column
    private double rating;
    @Column
    private String comment;

}
