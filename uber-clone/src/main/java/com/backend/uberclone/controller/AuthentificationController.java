package com.backend.uberclone.controller;

import com.backend.uberclone.dto.UserCredentialsDTO;
import com.backend.uberclone.dto.UserRequest;
import com.backend.uberclone.dto.UserTokenState;
import com.backend.uberclone.exception.ResourceConflictException;
import com.backend.uberclone.model.User;
import com.backend.uberclone.service.UserService;
import com.backend.uberclone.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/auth")
public class AuthentificationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody UserCredentialsDTO authenticationRequest, HttpServletResponse response) {

        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        }
        catch(AuthenticationException ae){
            return new ResponseEntity<>(new UserTokenState(), HttpStatus.NOT_FOUND);
        }


        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        System.out.println("ULOGOVAN" + user.getUsername());

        String role = user.getRoles().get(0).getName();
        // Vrati token kao odgovor na uspesnu autentifikaciju
        return new ResponseEntity<>(new UserTokenState(jwt, expiresIn, role), HttpStatus.OK);
    }


    @PostMapping("/usersignup")
    public ResponseEntity<User> addUser(@RequestBody UserRequest userRequest, UriComponentsBuilder ucBuilder) {

        User existUser = this.userService.findByUsername(userRequest.getUsername());

        if (existUser != null) {
            throw new ResourceConflictException(userRequest.getId(), "Username already exists");
        }
        User user = this.userService.save(userRequest);
        if(userRequest.getUserType().equals("client")){
            //emailService.sendActivationEmail(user);
        }
        else if(userRequest.getUserType().equals("admin")){

        }

        else{

        }

        System.out.println("Registrovan " + user.getUsername());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    }

