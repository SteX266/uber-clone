package com.backend.uberclone.rest;


import com.backend.uberclone.dto.*;
import com.backend.uberclone.model.ReservationType;
import com.backend.uberclone.model.VehicleType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        locations = {"classpath:application.properties", "classpath:application-reservation.properties"}
)
public class MakeReservationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;
    public void setUp(String email) {
        UserCredentialsDTO requestTestDriver = new UserCredentialsDTO();
        requestTestDriver.setEmail(email);
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
    public void testMakeInstantReservationUserHasOngoingReservation() throws Exception {
        setUp("serfezev@gmail.com");
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setVehicleType(VehicleType.ANY);
        reservationDTO.setReservationTime(LocalDateTime.now());
        reservationDTO.setStops(new ArrayList<String>(Arrays.asList("Puskinova 11, Novi Sad", "Gogoljeva 23, Novi Sad")));
        reservationDTO.setReservationType(ReservationType.INSTANT);
        reservationDTO.setHasPet(false);
        reservationDTO.setHasBaby(false);
        reservationDTO.setRouteGeoJson(new ArrayList<>(List.of("Route1")));
        reservationDTO.setCustomers(new ArrayList<>(List.of("serfezev@gmail.com")));
        reservationDTO.setStartCoordinates( new ArrayList<>(Arrays.asList(19.4, 45.6)));
        reservationDTO.setEndCoordinates(new ArrayList<>(Arrays.asList(19.2, 45.7)));
        reservationDTO.setEstimatedTime(80);
        reservationDTO.setEstimatedCost(130);
        reservationDTO.setDistance(0.34);
        HttpEntity<ReservationDTO> entity = new HttpEntity<>(reservationDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/reservation/makeReservation", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
    }

    @Test
    public void testMakeInstantReservationValidData() throws Exception {
        setUp("nikola@gmail.com");
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setVehicleType(VehicleType.ANY);
        reservationDTO.setReservationTime(LocalDateTime.now());
        reservationDTO.setStops(new ArrayList<String>(Arrays.asList("Puskinova 11, Novi Sad", "Gogoljeva 23, Novi Sad")));
        reservationDTO.setReservationType(ReservationType.INSTANT);
        reservationDTO.setHasPet(false);
        reservationDTO.setHasBaby(false);
        reservationDTO.setRouteGeoJson(new ArrayList<>(List.of("Route1")));
        reservationDTO.setCustomers(new ArrayList<>(List.of("nikola@gmail.com")));
        reservationDTO.setStartCoordinates( new ArrayList<>(Arrays.asList(19.4, 45.6)));
        reservationDTO.setEndCoordinates(new ArrayList<>(Arrays.asList(19.2, 45.7)));
        reservationDTO.setEstimatedTime(80);
        reservationDTO.setEstimatedCost(130);
        reservationDTO.setDistance(0.34);
        HttpEntity<ReservationDTO> entity = new HttpEntity<>(reservationDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/reservation/makeReservation", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testMakeScheduledReservationValidData() throws Exception {
        setUp("esteban@gmail.com");
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setVehicleType(VehicleType.ANY);
        reservationDTO.setReservationTime(LocalDateTime.now());
        reservationDTO.setStops(new ArrayList<String>(Arrays.asList("Puskinova 11, Novi Sad", "Gogoljeva 23, Novi Sad")));
        reservationDTO.setReservationType(ReservationType.SCHEDULED);
        reservationDTO.setHasPet(false);
        reservationDTO.setHasBaby(false);
        reservationDTO.setRouteGeoJson(new ArrayList<>(List.of("Route1")));
        reservationDTO.setCustomers(new ArrayList<>(List.of("esteban@gmail.com")));
        reservationDTO.setStartCoordinates( new ArrayList<>(Arrays.asList(19.4, 45.6)));
        reservationDTO.setEndCoordinates(new ArrayList<>(Arrays.asList(19.2, 45.7)));
        reservationDTO.setEstimatedTime(80);
        reservationDTO.setEstimatedCost(130);
        reservationDTO.setDistance(0.34);
        HttpEntity<ReservationDTO> entity = new HttpEntity<>(reservationDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/reservation/makeReservation", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
    @Test
    public void testMakeScheduledReservationUserHasOngoingReservation() throws Exception {
        setUp("serfezev@gmail.com");
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setVehicleType(VehicleType.ANY);
        reservationDTO.setReservationTime(LocalDateTime.now());
        reservationDTO.setStops(new ArrayList<String>(Arrays.asList("Puskinova 11, Novi Sad", "Gogoljeva 23, Novi Sad")));
        reservationDTO.setReservationType(ReservationType.SCHEDULED);
        reservationDTO.setHasPet(false);
        reservationDTO.setHasBaby(false);
        reservationDTO.setRouteGeoJson(new ArrayList<>(Arrays.asList("Route1")));
        reservationDTO.setCustomers(new ArrayList<>(Arrays.asList("serfezev@gmail.com")));
        reservationDTO.setStartCoordinates( new ArrayList<>(Arrays.asList(19.4, 45.6)));
        reservationDTO.setEndCoordinates(new ArrayList<>(Arrays.asList(19.2, 45.7)));
        reservationDTO.setEstimatedTime(80);
        reservationDTO.setEstimatedCost(130);
        reservationDTO.setDistance(0.34);
        HttpEntity<ReservationDTO> entity = new HttpEntity<>(reservationDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/reservation/makeReservation", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
    }

    @Test
    public void testMakeScheduledReservationNoCustomers() throws Exception {
        setUp("serfezev@gmail.com");
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setVehicleType(VehicleType.ANY);
        reservationDTO.setReservationTime(LocalDateTime.now());
        reservationDTO.setStops(new ArrayList<String>(Arrays.asList("Puskinova 11, Novi Sad", "Gogoljeva 23, Novi Sad")));
        reservationDTO.setReservationType(ReservationType.SCHEDULED);
        reservationDTO.setHasPet(false);
        reservationDTO.setHasBaby(false);
        reservationDTO.setRouteGeoJson(new ArrayList<>(Arrays.asList("Route1")));
        reservationDTO.setCustomers(new ArrayList<>());
        reservationDTO.setStartCoordinates( new ArrayList<>(Arrays.asList(19.4, 45.6)));
        reservationDTO.setEndCoordinates(new ArrayList<>(Arrays.asList(19.2, 45.7)));
        reservationDTO.setEstimatedTime(80);
        reservationDTO.setEstimatedCost(130);
        reservationDTO.setDistance(0.34);
        HttpEntity<ReservationDTO> entity = new HttpEntity<>(reservationDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/reservation/makeReservation", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
    }

    @Test
    public void testMakeReservationNoReservationTime() throws Exception {
        setUp("serfezev@gmail.com");
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setVehicleType(VehicleType.ANY);
        reservationDTO.setReservationTime(null);
        reservationDTO.setStops(new ArrayList<String>(Arrays.asList("Puskinova 11, Novi Sad", "Gogoljeva 23, Novi Sad")));
        reservationDTO.setReservationType(ReservationType.SCHEDULED);
        reservationDTO.setHasPet(false);
        reservationDTO.setHasBaby(false);
        reservationDTO.setRouteGeoJson(new ArrayList<>(Arrays.asList("Route1")));
        reservationDTO.setCustomers(new ArrayList<>(Arrays.asList("serfezev@gmail.com")));
        reservationDTO.setStartCoordinates( new ArrayList<>(Arrays.asList(19.4, 45.6)));
        reservationDTO.setEndCoordinates(new ArrayList<>(Arrays.asList(19.2, 45.7)));
        reservationDTO.setEstimatedTime(80);
        reservationDTO.setEstimatedCost(130);
        reservationDTO.setDistance(0.34);
        HttpEntity<ReservationDTO> entity = new HttpEntity<>(reservationDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/reservation/makeReservation", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
    }

    @Test
    public void testMakeReservationNoRoute() throws Exception {
        setUp("serfezev@gmail.com");
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setVehicleType(VehicleType.ANY);
        reservationDTO.setReservationTime(null);
        reservationDTO.setStops(new ArrayList<String>(Arrays.asList("Puskinova 11, Novi Sad", "Gogoljeva 23, Novi Sad")));
        reservationDTO.setReservationType(ReservationType.SCHEDULED);
        reservationDTO.setHasPet(false);
        reservationDTO.setHasBaby(false);
        reservationDTO.setRouteGeoJson(new ArrayList<>());
        reservationDTO.setCustomers(new ArrayList<>(Arrays.asList("serfezev@gmail.com")));
        reservationDTO.setStartCoordinates( new ArrayList<>(Arrays.asList(19.4, 45.6)));
        reservationDTO.setEndCoordinates(new ArrayList<>(Arrays.asList(19.2, 45.7)));
        reservationDTO.setEstimatedTime(80);
        reservationDTO.setEstimatedCost(130);
        reservationDTO.setDistance(0.34);
        HttpEntity<ReservationDTO> entity = new HttpEntity<>(reservationDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/reservation/makeReservation", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
    }

    @Test
    public void testMakeReservationRouteIsNull() throws Exception {
        setUp("serfezev@gmail.com");
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setVehicleType(VehicleType.ANY);
        reservationDTO.setReservationTime(null);
        reservationDTO.setStops(new ArrayList<String>(Arrays.asList("Puskinova 11, Novi Sad", "Gogoljeva 23, Novi Sad")));
        reservationDTO.setReservationType(ReservationType.SCHEDULED);
        reservationDTO.setHasPet(false);
        reservationDTO.setHasBaby(false);
        reservationDTO.setRouteGeoJson(null);
        reservationDTO.setCustomers(new ArrayList<>(Arrays.asList("serfezev@gmail.com")));
        reservationDTO.setStartCoordinates( new ArrayList<>(Arrays.asList(19.4, 45.6)));
        reservationDTO.setEndCoordinates(new ArrayList<>(Arrays.asList(19.2, 45.7)));
        reservationDTO.setEstimatedTime(80);
        reservationDTO.setEstimatedCost(130);
        reservationDTO.setDistance(0.34);
        HttpEntity<ReservationDTO> entity = new HttpEntity<>(reservationDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/reservation/makeReservation", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
    }

    @Test
    public void testMakeReservationCustomersIsNull() throws Exception {
        setUp("serfezev@gmail.com");
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setVehicleType(VehicleType.ANY);
        reservationDTO.setReservationTime(null);
        reservationDTO.setStops(new ArrayList<String>(Arrays.asList("Puskinova 11, Novi Sad", "Gogoljeva 23, Novi Sad")));
        reservationDTO.setReservationType(ReservationType.SCHEDULED);
        reservationDTO.setHasPet(false);
        reservationDTO.setHasBaby(false);
        reservationDTO.setRouteGeoJson(null);
        reservationDTO.setCustomers(null);
        reservationDTO.setStartCoordinates( new ArrayList<>(Arrays.asList(19.4, 45.6)));
        reservationDTO.setEndCoordinates(new ArrayList<>(Arrays.asList(19.2, 45.7)));
        reservationDTO.setEstimatedTime(80);
        reservationDTO.setEstimatedCost(130);
        reservationDTO.setDistance(0.34);
        HttpEntity<ReservationDTO> entity = new HttpEntity<>(reservationDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/reservation/makeReservation", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
    }

    @Test
    public void testMakeReservationNotLoggedIn() throws Exception {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setVehicleType(VehicleType.ANY);
        reservationDTO.setReservationTime(null);
        reservationDTO.setStops(new ArrayList<String>(Arrays.asList("Puskinova 11, Novi Sad", "Gogoljeva 23, Novi Sad")));
        reservationDTO.setReservationType(ReservationType.SCHEDULED);
        reservationDTO.setHasPet(false);
        reservationDTO.setHasBaby(false);
        reservationDTO.setRouteGeoJson(null);
        reservationDTO.setCustomers(new ArrayList<>(Arrays.asList("serfezev@gmail.com")));
        reservationDTO.setStartCoordinates( new ArrayList<>(Arrays.asList(19.4, 45.6)));
        reservationDTO.setEndCoordinates(new ArrayList<>(Arrays.asList(19.2, 45.7)));
        reservationDTO.setEstimatedTime(80);
        reservationDTO.setEstimatedCost(130);
        reservationDTO.setDistance(0.34);
        HttpEntity<ReservationDTO> entity = new HttpEntity<>(reservationDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/reservation/makeReservation", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }


    @Test
    public void testMakeScheduledReservationNoVehicleType() throws Exception {
        setUp("esteban@gmail.com");
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setVehicleType(null);
        reservationDTO.setReservationTime(LocalDateTime.now());
        reservationDTO.setStops(new ArrayList<String>(Arrays.asList("Puskinova 11, Novi Sad", "Gogoljeva 23, Novi Sad")));
        reservationDTO.setReservationType(ReservationType.SCHEDULED);
        reservationDTO.setHasPet(false);
        reservationDTO.setHasBaby(false);
        reservationDTO.setRouteGeoJson(new ArrayList<>(List.of("Route1")));
        reservationDTO.setCustomers(new ArrayList<>(List.of("esteban@gmail.com")));
        reservationDTO.setStartCoordinates( new ArrayList<>(Arrays.asList(19.4, 45.6)));
        reservationDTO.setEndCoordinates(new ArrayList<>(Arrays.asList(19.2, 45.7)));
        reservationDTO.setEstimatedTime(80);
        reservationDTO.setEstimatedCost(130);
        reservationDTO.setDistance(0.34);
        HttpEntity<ReservationDTO> entity = new HttpEntity<>(reservationDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/reservation/makeReservation", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
    }

    @Test
    public void testMakeScheduledReservationNoReservationType() throws Exception {
        setUp("esteban@gmail.com");
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setVehicleType(VehicleType.ANY);
        reservationDTO.setReservationTime(LocalDateTime.now());
        reservationDTO.setStops(new ArrayList<String>(Arrays.asList("Puskinova 11, Novi Sad", "Gogoljeva 23, Novi Sad")));
        reservationDTO.setReservationType(null);
        reservationDTO.setHasPet(false);
        reservationDTO.setHasBaby(false);
        reservationDTO.setRouteGeoJson(new ArrayList<>(List.of("Route1")));
        reservationDTO.setCustomers(new ArrayList<>(List.of("esteban@gmail.com")));
        reservationDTO.setStartCoordinates( new ArrayList<>(Arrays.asList(19.4, 45.6)));
        reservationDTO.setEndCoordinates(new ArrayList<>(Arrays.asList(19.2, 45.7)));
        reservationDTO.setEstimatedTime(80);
        reservationDTO.setEstimatedCost(130);
        reservationDTO.setDistance(0.34);
        HttpEntity<ReservationDTO> entity = new HttpEntity<>(reservationDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/reservation/makeReservation", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
    }

    @Test
    public void testMakeScheduledReservationNoStops() throws Exception {
        setUp("esteban@gmail.com");
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setVehicleType(VehicleType.ANY);
        reservationDTO.setReservationTime(LocalDateTime.now());
        reservationDTO.setStops(new ArrayList<String>());
        reservationDTO.setReservationType(ReservationType.SCHEDULED);
        reservationDTO.setHasPet(false);
        reservationDTO.setHasBaby(false);
        reservationDTO.setRouteGeoJson(new ArrayList<>(List.of("Route1")));
        reservationDTO.setCustomers(new ArrayList<>(List.of("esteban@gmail.com")));
        reservationDTO.setStartCoordinates( new ArrayList<>(Arrays.asList(19.4, 45.6)));
        reservationDTO.setEndCoordinates(new ArrayList<>(Arrays.asList(19.2, 45.7)));
        reservationDTO.setEstimatedTime(80);
        reservationDTO.setEstimatedCost(130);
        reservationDTO.setDistance(0.34);
        HttpEntity<ReservationDTO> entity = new HttpEntity<>(reservationDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/reservation/makeReservation", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
    }
    @Test
    public void testMakeScheduledReservationStopsNull() throws Exception {
        setUp("esteban@gmail.com");
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setVehicleType(VehicleType.ANY);
        reservationDTO.setReservationTime(LocalDateTime.now());
        reservationDTO.setStops(null);
        reservationDTO.setReservationType(ReservationType.SCHEDULED);
        reservationDTO.setHasPet(false);
        reservationDTO.setHasBaby(false);
        reservationDTO.setRouteGeoJson(new ArrayList<>(List.of("Route1")));
        reservationDTO.setCustomers(new ArrayList<>(List.of("esteban@gmail.com")));
        reservationDTO.setStartCoordinates( new ArrayList<>(Arrays.asList(19.4, 45.6)));
        reservationDTO.setEndCoordinates(new ArrayList<>(Arrays.asList(19.2, 45.7)));
        reservationDTO.setEstimatedTime(80);
        reservationDTO.setEstimatedCost(130);
        reservationDTO.setDistance(0.34);
        HttpEntity<ReservationDTO> entity = new HttpEntity<>(reservationDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/reservation/makeReservation", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
    }

    @Test
    public void testMakeScheduledReservationNoStartCoordinates() throws Exception {
        setUp("esteban@gmail.com");
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setVehicleType(VehicleType.ANY);
        reservationDTO.setReservationTime(LocalDateTime.now());
        reservationDTO.setStops(new ArrayList<String>(Arrays.asList("Puskinova 11, Novi Sad", "Gogoljeva 23, Novi Sad")));
        reservationDTO.setReservationType(ReservationType.SCHEDULED);
        reservationDTO.setHasPet(false);
        reservationDTO.setHasBaby(false);
        reservationDTO.setRouteGeoJson(new ArrayList<>(List.of("Route1")));
        reservationDTO.setCustomers(new ArrayList<>(List.of("esteban@gmail.com")));
        reservationDTO.setStartCoordinates( new ArrayList<>());
        reservationDTO.setEndCoordinates(new ArrayList<>(Arrays.asList(19.2, 45.7)));
        reservationDTO.setEstimatedTime(80);
        reservationDTO.setEstimatedCost(130);
        reservationDTO.setDistance(0.34);
        HttpEntity<ReservationDTO> entity = new HttpEntity<>(reservationDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/reservation/makeReservation", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
    }

    @Test
    public void testMakeScheduledReservationStartCoordinatesNull() throws Exception {
        setUp("esteban@gmail.com");
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setVehicleType(VehicleType.ANY);
        reservationDTO.setReservationTime(LocalDateTime.now());
        reservationDTO.setStops(new ArrayList<String>(Arrays.asList("Puskinova 11, Novi Sad", "Gogoljeva 23, Novi Sad")));
        reservationDTO.setReservationType(ReservationType.SCHEDULED);
        reservationDTO.setHasPet(false);
        reservationDTO.setHasBaby(false);
        reservationDTO.setRouteGeoJson(new ArrayList<>(List.of("Route1")));
        reservationDTO.setCustomers(new ArrayList<>(List.of("esteban@gmail.com")));
        reservationDTO.setStartCoordinates( null);
        reservationDTO.setEndCoordinates(new ArrayList<>(Arrays.asList(19.2, 45.7)));
        reservationDTO.setEstimatedTime(80);
        reservationDTO.setEstimatedCost(130);
        reservationDTO.setDistance(0.34);
        HttpEntity<ReservationDTO> entity = new HttpEntity<>(reservationDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/reservation/makeReservation", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
    }

    @Test
    public void testMakeScheduledReservationNoEndCoordinates() throws Exception {
        setUp("esteban@gmail.com");
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setVehicleType(VehicleType.ANY);
        reservationDTO.setReservationTime(LocalDateTime.now());
        reservationDTO.setStops(new ArrayList<String>(Arrays.asList("Puskinova 11, Novi Sad", "Gogoljeva 23, Novi Sad")));
        reservationDTO.setReservationType(ReservationType.SCHEDULED);
        reservationDTO.setHasPet(false);
        reservationDTO.setHasBaby(false);
        reservationDTO.setRouteGeoJson(new ArrayList<>(List.of("Route1")));
        reservationDTO.setCustomers(new ArrayList<>(List.of("esteban@gmail.com")));
        reservationDTO.setEndCoordinates( new ArrayList<>());
        reservationDTO.setStartCoordinates(new ArrayList<>(Arrays.asList(19.2, 45.7)));
        reservationDTO.setEstimatedTime(80);
        reservationDTO.setEstimatedCost(130);
        reservationDTO.setDistance(0.34);
        HttpEntity<ReservationDTO> entity = new HttpEntity<>(reservationDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/reservation/makeReservation", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
    }

    @Test
    public void testMakeScheduledReservationEndCoordinatesNull() throws Exception {
        setUp("esteban@gmail.com");
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setVehicleType(VehicleType.ANY);
        reservationDTO.setReservationTime(LocalDateTime.now());
        reservationDTO.setStops(new ArrayList<String>(Arrays.asList("Puskinova 11, Novi Sad", "Gogoljeva 23, Novi Sad")));
        reservationDTO.setReservationType(ReservationType.SCHEDULED);
        reservationDTO.setHasPet(false);
        reservationDTO.setHasBaby(false);
        reservationDTO.setRouteGeoJson(new ArrayList<>(List.of("Route1")));
        reservationDTO.setCustomers(new ArrayList<>(List.of("esteban@gmail.com")));
        reservationDTO.setEndCoordinates( null);
        reservationDTO.setStartCoordinates(new ArrayList<>(Arrays.asList(19.2, 45.7)));
        reservationDTO.setEstimatedTime(80);
        reservationDTO.setEstimatedCost(130);
        reservationDTO.setDistance(0.34);
        HttpEntity<ReservationDTO> entity = new HttpEntity<>(reservationDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/reservation/makeReservation", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
    }



}
