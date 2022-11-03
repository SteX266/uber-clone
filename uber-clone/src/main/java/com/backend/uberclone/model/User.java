package com.backend.uberclone.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String city;
    private String phoneNumber;
    private String profilePicture;
    private boolean deleted;
    private boolean banned;

    private UserType type;

}
