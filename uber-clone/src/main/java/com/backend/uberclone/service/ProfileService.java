package com.backend.uberclone.service;

import com.backend.uberclone.dto.UserDTO;
import com.backend.uberclone.model.User;
import com.backend.uberclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private UserRepository<User> userRepository;

    @Autowired
    private void setUserRepository(UserRepository<User> userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUserByEmail(String email) {
        return new UserDTO(userRepository.findUserByEmailAndBannedFalseAndEnabledTrueAndDeletedFalse(email));
    }

    public UserDTO getUserById(Integer id){
        return new UserDTO(userRepository.findOneById(id));
    }

}
