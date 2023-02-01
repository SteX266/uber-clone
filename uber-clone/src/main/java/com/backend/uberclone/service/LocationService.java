package com.backend.uberclone.service;

import com.backend.uberclone.dto.LocationDTO;
import com.backend.uberclone.model.Driver;
import com.backend.uberclone.model.Location;
import com.backend.uberclone.model.Ride;
import com.backend.uberclone.repository.DriverRepository;
import com.backend.uberclone.repository.LocationRepository;
import com.backend.uberclone.repository.RideRepository;
import com.backend.uberclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {


    private DriverRepository driverRepository;

    private RideRepository rideRepository;

    private LocationRepository locationRepository;

    @Autowired
    public void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Autowired
    public void setDriverRepository(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Autowired
    public void setRideRepository(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    // ovo treba da se nalazi u nekom driver servisu
    public boolean startShift(Integer driverId) {
        Driver driver = driverRepository.findOneById(driverId);
        if(driver == null) return false;
        driver.setActive(true);
        driverRepository.save(driver);
        return true;
    }

    // ovo treba u nekom driver servisu da se nalazi
    public boolean endShift(Integer driverId) {
        Driver driver = driverRepository.findOneById(driverId);
        if(driver == null) return false;
        driver.setActive(false);
        driver.setAvailable(false);
        driverRepository.save(driver);
        return true;
    }
    public boolean updateUserLocation(LocationDTO locationDTO) {
        Driver driver = driverRepository.findOneById(locationDTO.getDriverId());
        if(driver == null) return false;
        if(!driver.isActive()) return false;
        System.out.println(driver.getEmail());

        Location currentLocation = new Location(locationDTO.getLatitude(), locationDTO.getLongitude());
        locationRepository.save(currentLocation);
        driver.setCurrentLocation(currentLocation);
        driverRepository.save(driver);
        return true;
    }

    public List<LocationDTO> getActiveDriverLocations() {
        List<LocationDTO> locationDTOS = new ArrayList<>();
        List<Driver> activeDrivers = driverRepository.findAllByActive(true);

        for (Driver d: activeDrivers) {
            System.out.println(d.getEmail());
            Location location = d.getCurrentLocation();
            System.out.println("LOKACIJA JEBENA");
            System.out.println(location.getLatitude());
            System.out.println(location.getLongitude());
            locationDTOS.add(new LocationDTO(location.getLatitude(), location.getLongitude(), d.getId()));
        }
        return locationDTOS;
    }

    public LocationDTO getDriverLocationByRideId(Integer rideId) {
        Ride ride = rideRepository.findOneById(rideId);
        Driver driver = ride.getDriver();
        return new LocationDTO(driver.getCurrentLocation().getLatitude(), driver.getCurrentLocation().getLongitude(), driver.getId());
    }
}
