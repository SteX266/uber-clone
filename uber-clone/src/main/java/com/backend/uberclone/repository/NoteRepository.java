package com.backend.uberclone.repository;

import com.backend.uberclone.model.Note;
import com.backend.uberclone.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface NoteRepository  extends JpaRepository<Note, Integer> {
    public ArrayList<Note> findAllByUserId(int id);
}
