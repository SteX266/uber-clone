package com.backend.uberclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeDTO {
    private Integer rideId;
    private Double time;
}
