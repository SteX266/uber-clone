package com.backend.uberclone.repository;

import com.backend.uberclone.model.Reservation;
import com.backend.uberclone.model.ReservationStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void saveReservation(){
        Reservation r = new Reservation();
        r.setStatus(ReservationStatus.FINISHED);
        Reservation newReservation = reservationRepository.save(r);
        Assertions.assertNotNull(newReservation);
        Assertions.assertEquals(ReservationStatus.FINISHED, newReservation.getStatus());



    }

    @Test
    public void findOneById(){
        int id = 1;
        Reservation r = reservationRepository.findOneById(id);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(r.getStatus(), ReservationStatus.FINISHED);
    }

}
