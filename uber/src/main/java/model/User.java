package model;

import java.sql.Timestamp;
import java.util.List;

public class User {

    private Integer id;

    private String username;

    private String password;
    private Timestamp lastPasswordResetDate;

    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private boolean isDeleted;
    private boolean isEnabled;

    private List<Role> roles;

    private List<Ride> rides;
    private List<Review> reviews;
}
