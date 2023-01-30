package com.backend.uberclone.service;

import com.backend.uberclone.dto.CarDTO;
import com.backend.uberclone.model.*;
import com.backend.uberclone.repository.DriverRepository;
import com.backend.uberclone.repository.UpdateUserRequestRepository;
import com.backend.uberclone.repository.UserRepository;
import com.backend.uberclone.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DriverRepository driverRepository;
    @Autowired
    UpdateUserRequestRepository updateUserRequestRepository;

    public CarDTO getCarByUserId(Integer id) {
        if (userRepository.findOneById(id).getRoles().get(0).getName().equals("DRIVER"))  {
            return new CarDTO(vehicleRepository.getVehicleByDriverId(id));
        }
        return new CarDTO();
    }

    public boolean updateVehicle(CarDTO c) {
        Driver oldUser = driverRepository.findOneById(c.getDriver());


        System.out.println(oldUser);
        Vehicle oldVehicle = vehicleRepository.findOneById(c.getId());
        if (oldVehicle == null){
            return false;
        }
        UpdateUserRequest updateRequest  = new UpdateUserRequest();
        updateRequest.setUser(oldUser);
        updateRequest.setCity(oldUser.getCity());
        updateRequest.setName(oldUser.getName());
        updateRequest.setSurname(oldUser.getSurname());
        updateRequest.setPhoneNumber(oldUser.getPhoneNumber());
        updateRequest.setProfilePicture(oldUser.getProfilePicture());
        updateRequest.setAnswered(false);
        updateRequest.setEmail(oldUser.getEmail());
        Vehicle v = new Vehicle();
        v.setAllowsBaby(c.isAllowsBaby());
        v.setAllowsPet(c.isAllowsPet());
        v.setModel(c.getModel());
        v.setType(VehicleType.valueOf(c.getType()));
        v.setNumberOfSeats(c.getNumberOfSeats());
        updateRequest.setVehicle(vehicleRepository.save(v));
        updateUserRequestRepository.save(updateRequest);
        return true;
    }

    public CarDTO getCarByRequestId(Integer id) {
            UpdateUserRequest rq = updateUserRequestRepository.findOneById(id);
        return new CarDTO(vehicleRepository.getVehicleByUpdateRequestId(id),rq.getUser().getId());
    }
}
