package com.backend.uberclone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="user_table")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User implements UserDetails {

    @Id
    private Integer id;

    @Column
    private String email;

    @JsonIgnore
    @Column
    private String password;

    @Column(name="last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    @Column
    private String resetPasswordToken;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String city;

    @Column
    private String phoneNumber;

    @Column
    private String profilePicture;

    @Column
    private boolean deleted;

    @Column
    private boolean banned;

    @Column
    private boolean enabled;

    @Column
    private boolean socialLogin;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;



    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Message> sentMessages;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Note> notes;

    @OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Message> recievedMessages;
    @OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Notification> notifications;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UpdateUserRequest> updateRequests;


    public String getRole(){
        try{
            if(this.getRoles().get(0).getName().equals("ROLE_CLIENT")){
                return "CLIENT";
            }
            else if(this.getRoles().get(0).getName().equals("ROLE_DRIVER")){
                return "DRIVER";
            }
            else{
                return "ADMIN";
            }
        }
        catch(Exception e){
            return "";
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
