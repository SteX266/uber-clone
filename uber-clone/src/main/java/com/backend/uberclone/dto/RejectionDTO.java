package com.backend.uberclone.dto;


import com.backend.uberclone.model.Rejection;
import com.backend.uberclone.model.Ride;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RejectionDTO {
    private String reason;
    private Integer driverId;
    private Integer rideId;

    public Rejection createRejection(Ride ride) {
        Rejection rejection = new Rejection();
        rejection.setReason(this.reason);
        rejection.setRide(ride);
        return rejection;
    }
}
