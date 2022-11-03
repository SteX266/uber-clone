package model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Message {
    private User sender;
    private User recipient;
    private String text;
    private LocalDateTime date;
}
