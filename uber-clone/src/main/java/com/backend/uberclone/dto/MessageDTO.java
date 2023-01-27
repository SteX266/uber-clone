package com.backend.uberclone.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO  implements Comparable<MessageDTO>{

    int senderId;
    String senderEmail;
    String text;
    LocalDateTime date;
    String receiverEmail;
    int receiverId;

    public MessageDTO(Integer id, String email, String text, LocalDateTime date) {
        this.senderId = id;
        this.senderEmail = email;
        this.text = text;
        this.date = date;
    }

    @Override
    public int compareTo(@NotNull MessageDTO o) {
        return getDate().compareTo(o.getDate());

    }
}
