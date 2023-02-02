package com.backend.uberclone.service;

import com.backend.uberclone.dto.ReportDTO;
import com.backend.uberclone.model.Customer;
import com.backend.uberclone.model.Driver;
import com.backend.uberclone.model.Report;
import com.backend.uberclone.model.Ride;
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

    public List<ReportDTO> getUserReport(ReportDTO reportRequest) {
        List<ReportDTO> reports = new ArrayList<>();
        List<Ride> rides = new ArrayList<>();
        if (reportRequest.getUserRole().equals("client")){
            Customer c = customerRepository.findOneById(reportRequest.getUserId());
            rides = (List<Ride>) c.getRides();
        }
        else if(reportRequest.getUserRole().equals("driver")){
            Driver d = driverRepository.findOneById(reportRequest.getUserId());
            rides = d.getRides();
        }
        else{
            rides = rideRepository.findAll();
        }
        for (LocalDate date = reportRequest.getStartDate(); date.isEqual(reportRequest.getEndDate());date=date.plusDays(1)){
            int numberOfRides = 0;
            double numberOfKilometers = 0;
            double money = 0;
            for (Ride ride:rides){
                if (ride.getStartTime().toLocalDate().isEqual(date)){
                    numberOfRides++;
                    money += ride.getReservation().getEstimatedCost();
                    numberOfKilometers += ride.getReservation().getRoute().getDistanceInKm();
                }
            }
            ReportDTO dayReport = new ReportDTO(numberOfRides,numberOfKilometers,money);
            reports.add(dayReport);

        }
        return reports;

    }
}
