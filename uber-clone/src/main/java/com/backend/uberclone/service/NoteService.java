package com.backend.uberclone.service;

import com.backend.uberclone.dto.NoteDTO;
import com.backend.uberclone.model.Note;
import com.backend.uberclone.repository.NoteRepository;
import com.backend.uberclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    public  boolean addNote(NoteDTO note) {
        Note n = new Note();
        n.setDate(note.getDate());
        n.setText(note.getText());
        n.setUser(userRepository.findOneById(note.getUser()));
        noteRepository.save(n);
        return true;
    }

    public ArrayList<NoteDTO> getNotesByUserId(Integer id) {
        ArrayList<NoteDTO> notes = new ArrayList<>();
        for (Note note: noteRepository.findAllByUserId(id)) {
            notes.add(new NoteDTO(note));
        }
        return notes;
    }
}
