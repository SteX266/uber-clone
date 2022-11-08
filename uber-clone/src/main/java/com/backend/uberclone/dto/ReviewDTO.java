package com.backend.uberclone.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ReviewDTO {

    private int id;
    private Integer rideId;
    private String customerEmail;
    private String comment;
    private Integer carRating;
    private Integer driverRating;
}

