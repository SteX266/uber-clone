package com.backend.uberclone.dto;

import com.backend.uberclone.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {

    private Integer id;


    private String model;


    private int numberOfSeats;


    private boolean allowsPet;


    private boolean allowsBaby;


    private int driver;


    private String type;

    public CarDTO(Vehicle v){
        this.allowsBaby = v.isAllowsBaby();
        this.allowsPet = v.isAllowsPet();
        this.driver = v.getDriver().getId();
        this.id = v.getId();
        this.model = v.getModel();
        this.type = v.getType().toString();
        this.numberOfSeats = v.getNumberOfSeats();
    }
}
