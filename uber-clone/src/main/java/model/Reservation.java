package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reservation {
    private Ride ride;
    private String preferences;
    private boolean isFuture;

}
