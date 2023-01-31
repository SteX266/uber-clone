package com.backend.uberclone.dto;

import com.backend.uberclone.model.Note;
import com.backend.uberclone.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class NoteDTO {

    private int id;


    private int user;

    private String text;


    private LocalDateTime date;


   public NoteDTO(Note note){
        this.id = note.getId();
        this.user = note.getUser().getId();
        this.text = note.getText();
        this.date  = note.getDate();
    }

}
