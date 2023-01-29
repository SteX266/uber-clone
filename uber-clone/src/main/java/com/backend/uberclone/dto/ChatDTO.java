package com.backend.uberclone.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {

    private String email;
    private int id;
    private String lastMessage;
    private String name;
    private String surname;
    private String photoUrl;
}
