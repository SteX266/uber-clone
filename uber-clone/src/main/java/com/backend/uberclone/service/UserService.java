package com.backend.uberclone.service;


import com.backend.uberclone.dto.UserRequest;
import com.backend.uberclone.model.Role;
import com.backend.uberclone.model.User;
import com.backend.uberclone.repository.RoleRepository;
import com.backend.uberclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public User save(UserRequest userRequest) {
        User u = new User();
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

        roles.add(roleRepository.findOneById(1));
        u.setRoles(roles);

        return this.userRepository.save(u);
    }

    public User findOneById(Integer id) {
        return userRepository.findOneById(id);

    }

    public User saveUser(User user) {
        return this.userRepository.save(user);
    }
}
