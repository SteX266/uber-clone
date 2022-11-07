package com.backend.uberclone.controller;


import com.backend.uberclone.dto.UserDTO;
import com.backend.uberclone.model.User;
import com.backend.uberclone.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"}, allowedHeaders = "*")
@RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {


    @Autowired
    private ProfileService profileService;

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping(value = "/getProfileInfo")
    public ResponseEntity<UserDTO> getProfileInfo(@RequestParam String email){

            return new ResponseEntity<>(profileService.getProfileInfo(email), HttpStatus.OK);
    }

}
