package model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Driver extends User{
    private Vehicle vehicle;
    private boolean available;
    private List<ActivePeriod> activePeriods;
}
