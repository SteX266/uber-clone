package com.backend.uberclone.model;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@TypeDef(name = "json", typeClass = JsonType.class)
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="reservation")
    private Reservation reservation;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @Column
    @Enumerated(EnumType.STRING)
    private RideStatus status;



    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="rejection_id", referencedColumnName = "id")
    private Rejection rejection;

    @Column
    private int estimatedArrivalTimeInMinutes;


    public void cancelRide() {
        this.status = RideStatus.CANCELED;
        toggleCustomers(false); // ovo mozda i nije potrebno
        this.driver.setAvailable(true);
    }

    public void startRide() {
        this.status = RideStatus.ONGOING;
        toggleCustomers(true);
    }

    public void endRide() {
        this.status = RideStatus.FINISHED;
        toggleCustomers(false);
        this.driver.setAvailable(true);
    }

    public void abortRide() {
        this.status = RideStatus.ABORTED;
        toggleCustomers(false);
        this.driver.setAvailable(true);
    }

    private void toggleCustomers(boolean value) {
        for (Customer c: reservation.getCustomers()) {
            c.setRiding(value);
        }
    }

}
