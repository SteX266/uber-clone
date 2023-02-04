package com.backend.uberclone.rest;


import com.backend.uberclone.dto.*;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.testng.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        locations = {"classpath:application.properties", "classpath:application-ride.properties"}
)
public class RideControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;


    public void setUp() {
        UserCredentialsDTO requestTestDriver = new UserCredentialsDTO();
        requestTestDriver.setEmail("stevaszumza@gmail.com");
        requestTestDriver.setPassword("perica00");
        headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Access-Control-Allow-Origin", "*");
        HttpEntity<UserCredentialsDTO> httpEntity = new HttpEntity<>(requestTestDriver, headers);
        ResponseEntity<UserTokenState> resTest = restTemplate
                .exchange("/auth/login", HttpMethod.POST, httpEntity, UserTokenState.class);
        String tokenTestDriver = "Bearer " + resTest.getBody().getAccessToken();
        headers = new HttpHeaders();
        headers.add("Authorization", tokenTestDriver);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content-Type", "application/json");
    }

    @Test
    public void testStartRide(){
        setUp();
        RideDTO rideDTO = new RideDTO();
        rideDTO.setRideId(1);
        rideDTO.setDriverId(3);
        HttpEntity<RideDTO> entity = new HttpEntity<>(rideDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/ride/startRide", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
    @Test
    public void testStartRideNotFound(){
        setUp();
        RideDTO rideDTO = new RideDTO();
        rideDTO.setRideId(50);
        rideDTO.setDriverId(3);
        HttpEntity<RideDTO> entity = new HttpEntity<>(rideDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/ride/startRide", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    public void testStartRideAlreadyStarted(){
        setUp();
        RideDTO rideDTO = new RideDTO();
        rideDTO.setRideId(2);
        rideDTO.setDriverId(3);
        HttpEntity<RideDTO> entity = new HttpEntity<>(rideDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/ride/startRide", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.EXPECTATION_FAILED);

    }
    @Test
    public void testStartRideNotArrived(){
        setUp();
        RideDTO rideDTO = new RideDTO();
        rideDTO.setRideId(3);
        rideDTO.setDriverId(3);
        HttpEntity<RideDTO> entity = new HttpEntity<>(rideDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/ride/startRide", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.EXPECTATION_FAILED);

    }

    @Test
    public void testEndRide(){
        setUp();
        RideDTO rideDTO = new RideDTO();
        rideDTO.setRideId(2);
        rideDTO.setDriverId(3);
        HttpEntity<RideDTO> entity = new HttpEntity<>(rideDTO,headers);
        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/ride/endRide", entity, SuccessResponseDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
    @Test
    public void testEndRideDriverNotFound(){
        setUp();
        RideDTO rideDTO = new RideDTO();
        rideDTO.setRideId(2);
        rideDTO.setDriverId(50);
        HttpEntity<RideDTO> entity = new HttpEntity<>(rideDTO,headers);
        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/ride/endRide", entity, SuccessResponseDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

    }
    @Test
    public void testEndRideNotFound(){
        setUp();
        RideDTO rideDTO = new RideDTO();
        rideDTO.setRideId(50);
        rideDTO.setDriverId(3);
        HttpEntity<RideDTO> entity = new HttpEntity<>(rideDTO,headers);
        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/ride/endRide", entity, SuccessResponseDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    public void testRideNotFinishedYet(){
        setUp();
        RideDTO rideDTO = new RideDTO();
        rideDTO.setRideId(3);
        rideDTO.setDriverId(3);
        HttpEntity<RideDTO> entity = new HttpEntity<>(rideDTO,headers);
        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/ride/endRide", entity, SuccessResponseDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.EXPECTATION_FAILED);

    }

    @Test
    public void testRejectRide(){
        setUp();
        RejectionDTO rejectionDTO = new RejectionDTO();
        rejectionDTO.setRideId(3);
        rejectionDTO.setDriverId(3);
        rejectionDTO.setReason("Neki razlog");
        HttpEntity<RejectionDTO> entity = new HttpEntity<>(rejectionDTO,headers);
        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/ride/rejectRide", entity, SuccessResponseDTO.class);
        assertEquals(response.getStatusCode(),HttpStatus.OK);
    }
    @Test public void testRejectRideDriverNotFound(){
        setUp();
        RejectionDTO rejectionDTO = new RejectionDTO();
        rejectionDTO.setRideId(3);
        rejectionDTO.setDriverId(50);
        rejectionDTO.setReason("Neki razlog");
        HttpEntity<RejectionDTO> entity = new HttpEntity<>(rejectionDTO,headers);
        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/ride/rejectRide", entity, SuccessResponseDTO.class);
        assertEquals(response.getStatusCode(),HttpStatus.NOT_FOUND);
    }
    @Test public void testRejectRideNotFound(){
        setUp();
        RejectionDTO rejectionDTO = new RejectionDTO();
        rejectionDTO.setRideId(50);
        rejectionDTO.setDriverId(3);
        rejectionDTO.setReason("Neki razlog");
        HttpEntity<RejectionDTO> entity = new HttpEntity<>(rejectionDTO,headers);
        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/ride/rejectRide", entity, SuccessResponseDTO.class);
        assertEquals(response.getStatusCode(),HttpStatus.NOT_FOUND);
    }

    @Test public void testRejectRideAlreadyFinished(){
        setUp();
        RejectionDTO rejectionDTO = new RejectionDTO();
        rejectionDTO.setRideId(4);
        rejectionDTO.setDriverId(3);
        rejectionDTO.setReason("Neki razlog");
        HttpEntity<RejectionDTO> entity = new HttpEntity<>(rejectionDTO,headers);
        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/ride/rejectRide", entity, SuccessResponseDTO.class);
        assertEquals(response.getStatusCode(),HttpStatus.EXPECTATION_FAILED);
    }


}
