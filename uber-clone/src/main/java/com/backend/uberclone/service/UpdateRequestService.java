package com.backend.uberclone.service;

import com.backend.uberclone.dto.UserDTO;
import com.backend.uberclone.model.Driver;
import com.backend.uberclone.model.UpdateUserRequest;
import com.backend.uberclone.repository.DriverRepository;
import com.backend.uberclone.repository.UpdateUserRequestRepository;
import com.backend.uberclone.repository.UserRepository;
import com.backend.uberclone.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UpdateRequestService {

    @Autowired
    private UpdateUserRequestRepository updateUserRequestRepository;

   @Autowired
   private DriverRepository driverRepository;

   @Autowired
   private VehicleRepository vehicleRepository;

    public ArrayList<UserDTO> getAllUpdateUserRequests() {
        ArrayList<UserDTO> usersList  = new ArrayList<>();
        for (UpdateUserRequest u:updateUserRequestRepository.findAllByAnsweredFalse()) {
            usersList.add(new UserDTO(u));

        }
        return usersList;
    }

    public UserDTO getUpdateUserRequest(Integer id) {
        return(new UserDTO(updateUserRequestRepository.findOneById(id)));
    }

    public UserDTO getOldUserDataByRequestId(Integer id) {
        return(new UserDTO(updateUserRequestRepository.findOneById(id).getUser()));
    }


    public boolean acceptRequest(Integer id){
        UpdateUserRequest request = updateUserRequestRepository.findOneById(id);
        Driver d = driverRepository.findOneById(request.getUser().getId());
        if (request.getVehicle().getId() == d.getVehicle().getId()){
            return personalInfoUpdate(request,d);
        }
        return vehicleUpdate(request, d);
    }

    private boolean vehicleUpdate(UpdateUserRequest request, Driver d) {
        vehicleRepository.delete(d.getVehicle());
        d.setVehicle(request.getVehicle());
        driverRepository.save(d);
        request.setAnswered(true);
        updateUserRequestRepository.save(request);
        return true;
    }

    private boolean personalInfoUpdate(UpdateUserRequest request, Driver d) {

        d.setName(request.getName());
        d.setSurname(request.getSurname());
        d.setCity(request.getCity());
        d.setPhoneNumber(request.getPhoneNumber());
        d.setProfilePicture(request.getProfilePicture());
        driverRepository.save(d);
        request.setAnswered(true);
        updateUserRequestRepository.save(request);
        return true;
    }

    public boolean declineRequest(Integer id) {
        UpdateUserRequest request = updateUserRequestRepository.findOneById(id);
        request.setAnswered(true);
        updateUserRequestRepository.save(request);
        return true;
    }
}
