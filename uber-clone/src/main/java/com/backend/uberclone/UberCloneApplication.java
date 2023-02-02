package com.backend.uberclone;

import com.backend.uberclone.service.ScheduledReservationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Timer;

@SpringBootApplication
@EnableScheduling
public class UberCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(UberCloneApplication.class, args);

	}

}
