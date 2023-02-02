package com.backend.uberclone.dto;

import com.backend.uberclone.model.Customer;
import com.backend.uberclone.model.Ride;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RideHistoryDTO {

    private int id;
    private List<UserDTO> customers;
    private String vehicleType;
    private boolean hasBaby;
    private boolean hasPet;
    private String type;
    private String status;
    private double estimatedCost;
    private UserDTO driver;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<String> stops;

    public RideHistoryDTO(Ride ride){
        this.id = ride.getId();
        List<UserDTO> customers = new ArrayList<>();
        for (Customer c:ride.getReservation().getCustomers()){
            UserDTO customer = new UserDTO(c);
            customers.add(customer);
        }
        this.customers = customers;
        this.hasBaby = ride.getReservation().isHasBaby();
        this.hasPet = ride.getReservation().isHasPet();
        this.type = ride.getReservation().getType().toString();
        this.status = ride.getStatus().toString();
        this.estimatedCost = ride.getReservation().getEstimatedCost();
        this.driver = new UserDTO(ride.getDriver());
        this.startTime = ride.getStartTime();
        this.endTime = ride.getEndTime();
        this.stops = ride.getReservation().getRoute().getStops();
    }
}
