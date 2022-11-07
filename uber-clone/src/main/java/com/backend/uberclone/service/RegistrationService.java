package com.backend.uberclone.service;

import com.backend.uberclone.dto.RegistrationDTO;
import com.backend.uberclone.model.TransactionStatus;
import com.backend.uberclone.model.User;
import com.backend.uberclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RegistrationService {

    private UserRepository userRepository;

    @Autowired
    private void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // bilo bi kul da imamo neku klasu unutar koje je enkapsulirano stanje zavrsetka funkcije i poruka tog statusa a ne samo enumeracija
    @Transactional
    public TransactionStatus register(RegistrationDTO registrationDTO) {
        if(!registrationDTO.hasValidFields()) return TransactionStatus.INVALID_DATA;
        if(userRepository.existsByEmail(registrationDTO.getEmail())) return TransactionStatus.INVALID_DATA;
        User user = registrationDTO.createUser();
        userRepository.save(user);
        return TransactionStatus.SUCCESSFUL;
    }


}
