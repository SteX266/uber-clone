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

    @Override
    public int compareTo(@NotNull MessageDTO o) {
        return getDate().compareTo(o.getDate());

    }
}
