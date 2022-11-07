package com.backend.uberclone.dto;

import com.backend.uberclone.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegistrationDTO extends DTO {
    private String email;
    private String password;
    private String repeatPassword;
    private String name;
    private String surname;
    private String city;
    private String phoneNumber;

    public boolean passwordsMatch() {
        return this.password.equals(this.repeatPassword);
    }

    public boolean hasBlankFields() {
        return password.isBlank() || repeatPassword.isBlank() || email.isEmpty() || name.isBlank() || surname.isBlank()
                || city.isBlank() || phoneNumber.isBlank();
    }

    public boolean hasNullFields() {
        return password == null || repeatPassword == null || email == null || name == null || surname == null
                || city == null || phoneNumber == null;
    }

    public boolean hasValidFields() {
        if(hasNullFields()) return false;
        if(hasBlankFields()) return false;
        if(!passwordsMatch()) return false;
        return true;
    }
    public User createUser() {
        User u = new User();
        u.setName(name);
        u.setPassword(password);
        u.setEmail(email);
        u.setCity(city);
        u.setPhoneNumber(phoneNumber);
        return u;
    }
}
