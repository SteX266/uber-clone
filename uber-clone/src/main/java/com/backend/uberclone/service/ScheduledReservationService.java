package com.backend.uberclone.service;

import com.backend.uberclone.dto.DriverNewRideNotificationDTO;
import com.backend.uberclone.dto.PaymentDTO;
import com.backend.uberclone.dto.RejectionDTO;
import com.backend.uberclone.model.*;
import com.backend.uberclone.repository.CustomerRepository;
import com.backend.uberclone.repository.DriverRepository;
import com.backend.uberclone.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.TimerTask;

@Service
public class ScheduledReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    private DriverRepository driverRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RideService rideService;

    @Autowired
    private CustomerRepository customerRepository;


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public void setDriverRepository(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void deactivateOverworkedDrivers() {
        for (Driver d:driverRepository.findAllByActive(true)){
            if(d.isDriverOverworked()){
                userService.setDriverActivity(d,false);
                simpMessagingTemplate.convertAndSend("/driver/shift", new RejectionDTO("",-1,d.getId()));
            }
        }
    }
    @Scheduled(fixedRate = 60000)
    public void assignDueReservations() {
        for(Reservation r:reservationRepository.findAllByType(ReservationType.SCHEDULED)){
            if(r.getStatus() == ReservationStatus.ASSIGNMENT){
                if(LocalDateTime.now().plusMinutes(5).compareTo(r.getReservationTime())>0){

                    Ride newRide = this.rideService.createRide(r);
                    if (newRide== null) {
                        for (Payment p : r.getPayments()) {
                            simpMessagingTemplate.convertAndSend("/payment/all-confirmed", new PaymentDTO(p.getAmount(), r.getId(), p.getCustomer().getEmail(), true));
                            Customer c = p.getCustomer();
                            c.addCoins(p.getAmount());
                            customerRepository.save(c);
                        }
                        return;
                    }

                    if (newRide.getDriver().isAvailable()) {
                        simpMessagingTemplate.convertAndSend("/ride/new-ride", new DriverNewRideNotificationDTO(newRide.getId(), newRide.getDriver().getEmail()));
                        this.userService.setDriverAvailable(newRide.getDriver(), false);
                    }
                }
            }
        }

    }
}
