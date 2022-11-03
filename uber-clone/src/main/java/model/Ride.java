package model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Ride {
    private Route route;
    private List<Customer> costumers;
    private Driver driver;
    private double cost;
    private RideStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
