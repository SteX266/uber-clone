package com.backend.uberclone.controller;


import com.backend.uberclone.dto.UserDTO;
import com.backend.uberclone.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {


    @Autowired
    private ProfileService profileService;

    @GetMapping(value = "/getProfileInfo/{id}")
    public ResponseEntity<UserDTO> getProfileInfo(@PathVariable("id") String id){
        System.out.println("USAOO SAM BRAPOOOOO");
            return new ResponseEntity<>(profileService.getUserById(Integer.valueOf(id)), HttpStatus.OK);
    }

}
