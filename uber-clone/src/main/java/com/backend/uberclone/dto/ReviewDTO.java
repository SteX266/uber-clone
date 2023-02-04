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

    private Integer rideId;
    private String reviewerEmail;
    private String comment;
    private Integer vehicleRating;
    private Integer driverRating;
}

