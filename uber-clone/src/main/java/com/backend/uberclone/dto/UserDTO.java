package com.backend.uberclone.dto;

import com.backend.uberclone.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.sql.Timestamp;
@Getter
@Setter
public class UserDTO {
    private String email;

    private String name;

    private String surname;

    private String city;

    private String phoneNumber;

    private String profilePicture;

    private String role;

    public UserDTO(User u){
        email = u.getEmail();
        name = u.getName();
        surname = u.getSurname();
        city = u.getCity();
        phoneNumber = u.getPhoneNumber();
        profilePicture = u.getProfilePicture();
        role = "client";
    }
}

