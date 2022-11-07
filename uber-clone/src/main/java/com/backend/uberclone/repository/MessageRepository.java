package com.backend.uberclone.repository;

import com.backend.uberclone.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
