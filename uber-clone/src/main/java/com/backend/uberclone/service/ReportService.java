package com.backend.uberclone.service;

import com.backend.uberclone.dto.ReportDTO;
import com.backend.uberclone.dto.ReportListsDTO;
import com.backend.uberclone.model.*;
import com.backend.uberclone.repository.CustomerRepository;
import com.backend.uberclone.repository.DriverRepository;
import com.backend.uberclone.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {


    @Autowired
    private RideRepository rideRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DriverRepository driverRepository;

    public ReportListsDTO getUserReport(ReportDTO reportRequest) {
        ReportListsDTO lists = new ReportListsDTO();
        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Double> prices = new ArrayList<>();
        ArrayList<Integer> numberOfRides = new ArrayList<>();
        ArrayList<Double> distance = new ArrayList<>();
        List<Ride> rides;
        if (reportRequest.getUserRole().equals("CLIENT")){
            rides = new ArrayList<>();
            Customer c = this.customerRepository.findOneById(reportRequest.getUserId());
            for(Reservation r:c.getReservations()){
                if(r.getRide() != null){
                    rides.add(r.getRide());
                }
            }
        }
        else if(reportRequest.getUserRole().equals("DRIVER")){
            Driver d = driverRepository.findOneById(reportRequest.getUserId());
            rides = d.getRides();
        }
        else{
            rides = rideRepository.findAll();
        }
        for (LocalDate date = reportRequest.getStartDate(); date.compareTo(reportRequest.getEndDate())<=0;date=date.plusDays(1)){
            labels.add(date.toString());
            int nOfRides = 0;
            double numberOfKilometers = 0;
            double money = 0;
            for (Ride ride:rides){
                if (ride.getStartTime().toLocalDate().isEqual(date)){
                    nOfRides++;
                    money += ride.getReservation().getEstimatedCost();
                    numberOfKilometers += ride.getReservation().getRoute().getDistanceInKm();
                }
            }

            numberOfRides.add(nOfRides);
            prices.add(money);
            distance.add(numberOfKilometers);
        }
        lists.setLabels(labels);
        lists.setDistance(distance);
        lists.setNumberOfRides(numberOfRides);
        lists.setPrices(prices);
        return lists;

    }
}
