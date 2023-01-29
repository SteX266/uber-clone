package com.backend.uberclone.service;


import com.backend.uberclone.dto.SocialUserCredentialsDTO;
import com.backend.uberclone.dto.UserDTO;
import com.backend.uberclone.dto.UserRequest;
import com.backend.uberclone.model.*;
import com.backend.uberclone.repository.RoleRepository;
import com.backend.uberclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository<User> userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    public User findByUsername(String email) {
        return userRepository.findOneByEmail(email);

    }

    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        User user = userRepository.findOneByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("Could not find any user with the email " + email);
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    public boolean changePassword(User user, String newPassword, String oldPassword){
        if (passwordEncoder.matches(oldPassword,user.getPassword())){
            String newHashedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(newHashedPassword);
            user.setLastPasswordResetDate(new Timestamp(new Date().getTime()));
            userRepository.save(user);
            return true;
        }
        return false;
    }
    public User saveSocialUser(SocialUserCredentialsDTO userRequest){
        User u = new Customer();
        u.setEmail(userRequest.getEmail());
        u.setName(userRequest.getFirstName());
        u.setSurname(userRequest.getLastName());
        u.setEnabled(true);
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findOneById(2));
        u.setRoles(roles);
        u.setId(this.generateNextId());

        return this.userRepository.save(u);

    }

    public Integer generateNextId(){
        Integer id = 0;
        List<User> users = userRepository.findAll();
        int numberOfUsers = users.size();
        if ( numberOfUsers == 0){
            id = 1;
        }
        else{
            id = users.get(numberOfUsers - 1).getId() + 1;
        }
        System.out.println("SLEDECI ID JE" + id);

        return id;
    }
    public User save(UserRequest userRequest) {
        User u;
        if (userRequest.getUserType().equals("driver")){
            u = new Driver();
        }
        else{
            u = new Customer();
        }
        u.setEmail(userRequest.getEmail());

        // pre nego sto postavimo lozinku u atribut hesiramo je kako bi se u bazi nalazila hesirana lozinka
        // treba voditi racuna da se koristi isi password encoder bean koji je postavljen u AUthenticationManager-u kako bi koristili isti algoritam
        u.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        u.setName(userRequest.getName());
        u.setSurname(userRequest.getSurname());
        u.setEnabled(false);
        u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        u.setPhoneNumber(userRequest.getPhoneNumber());
        u.setCity(userRequest.getCity());
        // u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
        List<Role> roles = new ArrayList<>();
        u.setId(this.generateNextId());
        if(userRequest.getUserType().equals("client")){
            roles.add(roleRepository.findOneById(2));
            u.setRoles(roles);
            return this.userRepository.save(u);
        }
        else if(userRequest.getUserType().equals("driver")){
            Driver d = (Driver) u;
            Vehicle v = new Vehicle(userRequest.getCarModel(), userRequest.getNumberOfSeats(), userRequest.isPetFriendly(), userRequest.isChildrenFriendly(), userRequest.getVehicleType());
            d.setVehicle(v);
            roles.add(roleRepository.findOneById(3));
            d.setRoles(roles);
            d.setEnabled(true);
            return this.userRepository.save(d);
        }
        else{
            roles.add(roleRepository.findOneById(1));
            u.setRoles(roles);
            return this.userRepository.save(u);
        }

    }

    public User findOneById(Integer id) {
        return userRepository.findOneById(id);

    }


    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    public boolean updateUser(UserDTO u) {
        User oldUser = userRepository.findOneByEmail(u.getEmail());
        if (oldUser == null){
            return false;
        }
        oldUser.setName(u.getName());
        oldUser.setSurname(u.getSurname());
        oldUser.setCity(u.getCity());
        oldUser.setPhoneNumber( u.getPhoneNumber());
        oldUser.setProfilePicture( u.getProfilePicture());
        userRepository.save(oldUser);
        return true;
        }

    }
