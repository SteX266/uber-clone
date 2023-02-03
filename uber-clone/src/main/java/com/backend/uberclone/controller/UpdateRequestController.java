package com.backend.uberclone.controller;


import com.backend.uberclone.dto.UserDTO;
import com.backend.uberclone.service.UpdateRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/updateRequest")
public class UpdateRequestController {

    @Autowired
    private UpdateRequestService updateRequestService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/getAllUpdateUserRequests")
    public ResponseEntity<ArrayList<UserDTO>> getAllUpdateUserRequests(){
        return new ResponseEntity<>(updateRequestService.getAllUpdateUserRequests(), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/getUpdateUserRequest/{id}")
    public ResponseEntity<UserDTO> getUpdateUserRequest(@PathVariable("id") String id){
        return new ResponseEntity<>(updateRequestService.getUpdateUserRequest(Integer.valueOf(id)), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/getOldUserDataByRequestId/{id}")
    public ResponseEntity<UserDTO> getOldUserDataByRequestId(@PathVariable("id") String id){
        return new ResponseEntity<>(updateRequestService.getOldUserDataByRequestId(Integer.valueOf(id)), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/acceptRequest/{id}")
    public ResponseEntity<Boolean> acceptRequest(@PathVariable("id") String id){
        return new ResponseEntity<>(updateRequestService.acceptRequest(Integer.valueOf(id)), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/declineRequest/{id}")
    public ResponseEntity<Boolean> declineRequest(@PathVariable("id") String id){
        return new ResponseEntity<>(updateRequestService.declineRequest(Integer.valueOf(id)), HttpStatus.OK);
    }

}
