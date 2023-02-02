package com.backend.uberclone.service;

import com.backend.uberclone.dto.DriverNewRideNotificationDTO;
import com.backend.uberclone.dto.RejectionDTO;
import com.backend.uberclone.model.Driver;
import com.backend.uberclone.model.Reservation;
import com.backend.uberclone.model.ReservationStatus;
import com.backend.uberclone.model.ReservationType;
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
                    //assign reservation
                }
            }
        }

    }
}
