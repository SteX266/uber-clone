package com.backend.uberclone.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="update_request")
public class UpdateUserRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String city;

    @Column
    private String phoneNumber;

    @Column
    private String profilePicture;

    @Column
    private boolean answered;

    @Column
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="vehicle_id")
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
