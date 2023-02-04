package com.backend.uberclone.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String model;

    @Column
    private int numberOfSeats;

    @Column
    private boolean allowsPet;

    @Column
    private boolean allowsBaby;

    @OneToOne(mappedBy = "vehicle")
    private Driver driver;


    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UpdateUserRequest> updateRequest;

    @Column
    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", allowsPet=" + allowsPet +
                ", allowsBaby=" + allowsBaby +
                ", driver=" + driver +
                ", updateRequest=" + updateRequest +
                ", type=" + type +
                '}';
    }

    public Vehicle(String carModel, int numberOfSeats, boolean allowsPet, boolean allowsBaby, String vehicleType){
        this.model = carModel;
        this.numberOfSeats = numberOfSeats;
        this.allowsBaby = allowsBaby;
        this.allowsPet = allowsPet;
        if (vehicleType.equals("PREMIUM")){
            this.type = VehicleType.PREMIUM;
        }
        else if(vehicleType.equals("ECO")){
            this.type = VehicleType.ECO;
        }
        else{
            this.type = VehicleType.REGULAR;
        }




    }
}
