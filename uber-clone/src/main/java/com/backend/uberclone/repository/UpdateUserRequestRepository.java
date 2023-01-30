package com.backend.uberclone.repository;

import com.backend.uberclone.model.UpdateUserRequest;
import com.backend.uberclone.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface UpdateUserRequestRepository extends JpaRepository<UpdateUserRequest, Integer> {
    public UpdateUserRequest findOneById(int id);
    public ArrayList<UpdateUserRequest> findAllByAnsweredFalse();
}
