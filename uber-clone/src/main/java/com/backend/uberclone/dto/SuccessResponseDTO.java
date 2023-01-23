package com.backend.uberclone.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SuccessResponseDTO {
    private String status;

    public SuccessResponseDTO(){
        this.status = "OK";
    }
}
