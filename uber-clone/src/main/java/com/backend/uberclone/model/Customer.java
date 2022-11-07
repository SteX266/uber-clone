package com.backend.uberclone.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer extends User {

    @Column
    private boolean riding;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FavouriteRoute> favoriteRoutes;

    @OneToMany(mappedBy = "reviewer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> given_reviews;

    @ManyToMany
    @JoinTable(name = "rides",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ride_id", referencedColumnName = "id"))
    private Set<Ride> rides;

    @ManyToMany
    @JoinTable(name = "reservations",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ride_id", referencedColumnName = "id"))
    private Set<Reservation> reservations;

    // ovde treba da postoji payment method
}
