package com.backend.uberclone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportListsDTO {

    private ArrayList<String> labels;
    private ArrayList<Double> prices;
    private ArrayList<Integer> numberOfRides;
    private ArrayList<Double> distance;

}
