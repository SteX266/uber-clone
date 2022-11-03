package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rejection {
    private String reason;
    private User sender;
}
