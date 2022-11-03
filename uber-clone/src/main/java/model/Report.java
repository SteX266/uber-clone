package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Report {
    private String comment;
    private User recipient;
    private User reporter;
}
