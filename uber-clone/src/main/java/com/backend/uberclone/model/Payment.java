package com.backend.uberclone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column

    private double amount;

    @JoinColumn(name="customer_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    @JoinColumn(name="reservation_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Reservation reservation;

    @Column
    private boolean paid;

    public Payment(Customer customer, double amount) {
        this.customer = customer;
        this.amount = amount;
        this.paid = false;
    }

}
