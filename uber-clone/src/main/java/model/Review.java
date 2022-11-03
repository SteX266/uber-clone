package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review {
    private User reviewer;
    private User recipient;
    private double rating;
    private String comment;

}
