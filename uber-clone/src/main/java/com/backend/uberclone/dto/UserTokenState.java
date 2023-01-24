package com.backend.uberclone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTokenState {

    private String accessToken;
    private int expiresIn;
    private String userRole;
    private String email;
    private Integer id;
}