package com.backend.uberclone.dto;

import com.backend.uberclone.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegistrationDTO {
    private String email;
    private String password;
    private String repeatPassword;
    private String name;
    private String surname;
    private String city;
    private String phoneNumber;
    private String vehicleModel;
    private int numberOfSeats;
    private VehicleType vehicleType;

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
        return passwordsMatch();
    }

    public boolean hasNullFieldsDriver() {
        return hasNullFields() && (vehicleModel == null || vehicleType == null);
    }

    public boolean hasBlankFieldsDriver() {
        return hasBlankFields() && vehicleModel.isBlank();
    }

    public boolean validNumberOfSeats() {
        return numberOfSeats >= 0 && numberOfSeats <= 10;
    }

    public boolean hasValidFieldsDriver() {
        if(hasNullFieldsDriver()) return false;
        if(hasBlankFieldsDriver()) return false;
        if(!validNumberOfSeats()) return false;
        return passwordsMatch();
    }

    // ovo je upitno
    public Customer createCustomer() {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPassword(password); // treba da se uradi hesovanje sifre
        customer.setEmail(email);
        customer.setCity(city);
        customer.setPhoneNumber(phoneNumber);
        return customer;
    }
    // ovo je upitno
    public Driver createDriver() {
        return new Driver();
    }


}
