package com.backend.uberclone.service;

import com.backend.uberclone.dto.CarDTO;
import com.backend.uberclone.model.Role;
import com.backend.uberclone.model.Vehicle;
import com.backend.uberclone.model.VehicleType;
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

    public CarDTO getCarByUserId(Integer id) {
        if (userRepository.findOneById(id).getRoles().get(0).getName().equals("DRIVER"))  {
            return new CarDTO(vehicleRepository.getVehicleByDriverId(id));
        }
        return new CarDTO();
    }

    public boolean updateVehicle(CarDTO c) {
        Vehicle oldVehicle = vehicleRepository.findOneById(c.getId());
        if (oldVehicle == null){
            return false;
        }
        oldVehicle.setAllowsBaby(c.isAllowsBaby());
        oldVehicle.setAllowsPet(c.isAllowsPet());
        oldVehicle.setModel(c.getModel());
        oldVehicle.setType(VehicleType.valueOf(c.getType()));
        oldVehicle.setNumberOfSeats(c.getNumberOfSeats());
        vehicleRepository.save(oldVehicle);
        return true;
    }
}
