package com.backend.uberclone.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="reservation")
    private Reservation reservation;

    @ManyToMany(mappedBy = "rides")
    private Set<Customer> customers;

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

    @OneToMany(mappedBy = "ride", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews;
    public void cancel() {
        this.status = RideStatus.CANCELED;
        toggleCustomers(false); // ovo mozda i nije potrebno
    }

    public void start() {
        this.status = RideStatus.ONGOING;
        toggleCustomers(true);
    }

    public void end() {
        this.status = RideStatus.FINISHED;
        toggleCustomers(false);
    }

    public void abort() {
        this.status = RideStatus.ABORTED;
        toggleCustomers(false);
    }

    private void toggleCustomers(boolean value) {
        for (Customer c: customers) {
            c.setRiding(value);
        }
    }

}
