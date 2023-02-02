package com.backend.uberclone.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Driver extends User {


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    @Column
    private boolean available;

    @Column
    private boolean active;

    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ActivePeriod> activePeriods;

    @OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> receivedReviews;

    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ride> rides;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "current_location_id", referencedColumnName = "id")
    private Location currentLocation;



    public boolean isDriverOverworked() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        double totalTimeInSeconds = 0;
        for (ActivePeriod period:activePeriods){
            if(period.getStart().compareTo(yesterday) > 0){
                if(period.getEnd() == null){
                    totalTimeInSeconds += period.getStart().until(LocalDateTime.now(), ChronoUnit.SECONDS);
                }
                else{
                    totalTimeInSeconds += period.getStart().until(period.getEnd(), ChronoUnit.SECONDS);
                }
            }
            else if(period.getEnd() != null){
                if(period.getEnd().compareTo(yesterday) > 0){
                    totalTimeInSeconds += yesterday.until(period.getEnd(), ChronoUnit.SECONDS);
                }
            }
        }
        if (totalTimeInSeconds < 28500){
            return false;
        }
        return true;

    }

    public Ride getNextRide() {
        for (Ride r:rides){
            if(r.getStatus() == RideStatus.ARRIVING){
                return r;
            }
        }
        return null;
    }

    public void addActivePeriod(ActivePeriod activePeriod) {
        this.activePeriods.add(activePeriod);
    }
}
