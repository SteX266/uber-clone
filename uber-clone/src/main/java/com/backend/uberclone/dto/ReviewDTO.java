package com.backend.uberclone.dto;
import com.backend.uberclone.model.Review;
import com.backend.uberclone.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ReviewDTO {

    private int id;
    private String client;
    private String driver;

    private String car;
    private int driverRating;
    private int carRating;
    private String comment;
}

    public ReviewDTO(Review r){
        this.id = r.getId();
        this.driver = r.get;
        this.text = r.getText();
        this.client = r.getReviewer().getEmail();
        this.systemEntity = r.getSystemEntity().getName();
        this.owner= r.getSystemEntity().getOwner().getUsername();

    }
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="reviewer_id")
    private User reviewer;

    @Column
    private double carRating;
    @Column
    private double criverRating;
    @Column
    private String comment;