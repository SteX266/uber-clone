package com.backend.uberclone.service;

import com.backend.uberclone.dto.UserDTO;
import com.backend.uberclone.model.Driver;
import com.backend.uberclone.model.Review;
import com.backend.uberclone.model.User;
import com.backend.uberclone.repository.DriverRepository;
import com.backend.uberclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private UserRepository<User> userRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private void setUserRepository(UserRepository<User> userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUserByEmail(String email) {
        return new UserDTO(userRepository.findUserByEmailAndBannedFalseAndEnabledTrueAndDeletedFalse(email));
    }

    public UserDTO getUserById(Integer id){
        User u = userRepository.findOneById(id);
        double driverScore;
        double vehicleScore;
        if (u.getRole().equals("DRIVER")){
            driverScore = 0;
            vehicleScore = 0;
            Driver d = driverRepository.findOneById(id);
            for(Review review:d.getReceivedReviews()){
                driverScore += review.getDriverRating();
                vehicleScore += review.getCarRating();
            }
            int numberOfReviews = d.getReceivedReviews().size();
            if(numberOfReviews != 0){
                driverScore = driverScore / numberOfReviews;
                vehicleScore = vehicleScore /numberOfReviews;
            }
        }
        else{
            driverScore = 0;
            vehicleScore = 0;
        }
        return new UserDTO(userRepository.findOneById(id), driverScore, vehicleScore);
    }

    public boolean banUser(Integer id) {
        User u = userRepository.findOneById(id);
        u.setBanned(true);
        userRepository.save(u);
        u = userRepository.findOneById(id);
        return u.isBanned();

    }
}
