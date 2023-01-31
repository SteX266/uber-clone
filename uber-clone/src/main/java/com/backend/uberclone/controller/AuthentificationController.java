package com.backend.uberclone.controller;

import com.backend.uberclone.dto.*;
import com.backend.uberclone.exception.ResourceConflictException;
import com.backend.uberclone.model.User;
import com.backend.uberclone.service.EmailService;
import com.backend.uberclone.service.UserService;
import com.backend.uberclone.util.TokenUtils;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

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

    @Autowired
    private EmailService emailService;


    @PostMapping("/forgotPassword")
    public ResponseEntity<SuccessResponseDTO> processForgotPassword(@RequestBody ForgotPasswordRequestDTO request){
        System.out.println(request.getEmail());


        String token = RandomString.make(30);

        try {
            userService.updateResetPasswordToken(token, request.getEmail());
            String resetPasswordLink =  "http://localhost:4200/reset_password?token=" + token;
            emailService.sendResetPasswordEmail(request.getEmail(),resetPasswordLink);

        } catch (UsernameNotFoundException ex) {
            return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);



    }

    @PostMapping("/resetPassword")
    public ResponseEntity<SuccessResponseDTO> processResetPassword(@RequestBody ResetPasswordDTO resetPasswordRequest){
        User user = userService.getByResetPasswordToken(resetPasswordRequest.getToken());

        if (user == null) {
            return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.NOT_FOUND);

        } else {
            userService.updatePassword(user, resetPasswordRequest.getNewPassword());
            return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);
        }
    }
    @PostMapping("/loginSocial")
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody SocialUserCredentialsDTO socialUserCredentialsDTO){
        System.out.println("EMAIIASLSGLALAS");
        System.out.println(socialUserCredentialsDTO.getEmail());
        User user = userService.findByUsername(socialUserCredentialsDTO.getEmail());

        if(user == null){
            user = userService.saveSocialUser(socialUserCredentialsDTO);
        }
        if (!user.isEnabled()){
            return new ResponseEntity<>(new UserTokenState(), HttpStatus.NOT_FOUND);

        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null,user.getRoles());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();
        String role = user.getRoles().get(0).getName();
        System.out.println("ULOGOVAN" + user.getUsername());

        return new ResponseEntity<>(new UserTokenState(jwt, expiresIn, role, user.getEmail(), user.getId()), HttpStatus.OK);
    }


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
        if(role.equals("DRIVER")){
            userService.setDriverActive(user.getId());
        }
        // Vrati token kao odgovor na uspesnu autentifikaciju
        return new ResponseEntity<>(new UserTokenState(jwt, expiresIn, role, user.getEmail(), user.getId()), HttpStatus.OK);
    }


    @PostMapping("/usersignup")
    public ResponseEntity<User> addUser(@RequestBody UserRequest userRequest, UriComponentsBuilder ucBuilder) {
        System.out.println("POCELA REGISTRACIJA");
        User existUser = this.userService.findByUsername(userRequest.getEmail());

        if (existUser != null) {
            throw new ResourceConflictException(userRequest.getId(), "Username already exists");
        }
        User user = this.userService.save(userRequest);
        if(userRequest.getUserType().equals("client")){
            emailService.sendActivationEmail(user);

        }
        else if(userRequest.getUserType().equals("admin")){

        }

        else{

        }

        System.out.println("Registrovan " + user.getUsername());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/activate/{id}")
    public ResponseEntity<String> activateUser(@PathVariable Integer id){
        User user = userService.findOneById(id);

        if(!user.isEnabled()){
            user.setEnabled(true);
            userService.saveUser(user);
            System.out.println("USPESNA AKTIVACIJA NALOGA!");
            return new ResponseEntity<>("Uspesna aktivacija!", HttpStatus.FOUND);
        }
        return new ResponseEntity<>("Neuspesna aktivacija!", HttpStatus.NOT_FOUND);

    }
    }

