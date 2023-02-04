package com.backend.uberclone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SocialUserCredentialsDTO {
    private String email;
    private String firstName;
    private String id;
    private String lastName;
    private String name;
    private String photoUrl;
    private String provider;
}
