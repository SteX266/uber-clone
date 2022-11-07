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
    private int rideId;
    private String customer;
    private String comment;
    private int carRating;
    private int driverRating;
}

