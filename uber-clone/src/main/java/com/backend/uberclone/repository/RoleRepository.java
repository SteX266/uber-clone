package com.backend.uberclone.repository;

import com.backend.uberclone.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {


    Role findOneById(int i);
}
