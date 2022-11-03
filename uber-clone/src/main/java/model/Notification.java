package model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Notification {
    private User recipient;
    private User sender;
    private LocalDateTime date;
    private NotificationType type;
    private boolean read;

}
