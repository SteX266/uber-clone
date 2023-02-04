package com.backend.uberclone.rest;

import com.backend.uberclone.dto.*;

import com.backend.uberclone.repository.ReservationRepository;
import com.backend.uberclone.service.ReservationService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.testng.Assert.assertNotEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        "classpath:application.properties"
)
public class ReservationControllerTest {





    @Autowired
    ReservationService reservationService;

    @MockBean
    ReservationRepository reservationRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    @BeforeEach
    public void setUp() {
        UserCredentialsDTO requestTestDriver = new UserCredentialsDTO();
        requestTestDriver.setEmail("test@gmail.com");
        requestTestDriver.setPassword("pass");
        ResponseEntity<UserTokenState> resTest = restTemplate
                .postForEntity("/auth/login/", requestTestDriver, UserTokenState.class);
        String tokenTestDriver = "Bearer " + resTest.getBody().getAccessToken();
        headers = new HttpHeaders();
        headers.add("Authorization", tokenTestDriver);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content-Type", "application/json");
    }
    @Test
    public void testConfirmPaymentNoValidId() throws Exception {
        // this.reservationService.makeReservation(new ReservationDTO(new ArrayList<String>(Arrays.asList("Puskinova 11, Novi Sad", "Gogoljeva 23, Novi Sad")), LocalDateTime.now(), new ArrayList<>(Arrays.asList("Route1")), new ArrayList<>(Arrays.asList("serfezev@gmail.com")), VehicleType.ANY, ReservationType.INSTANT, false, false, 0.34, 80, 130, new ArrayList<>(Arrays.asList(19.4, 45.6)), new ArrayList<>(Arrays.asList(19.2, 45.7))));
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setReservationId(1);
        paymentDTO.setCanceled(false);
        paymentDTO.setAmount(100);
        paymentDTO.setRideId(1);
        paymentDTO.setCustomerEmail("serfezev@gmail.com");
        HttpEntity<PaymentDTO> entity = new HttpEntity<>(paymentDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/api/reservation/confirmPayment", entity, SuccessResponseDTO.class);

        assertNotEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testCancelPaymentNoReservationId() {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setReservationId(1);
        paymentDTO.setCanceled(false);
        paymentDTO.setAmount(100);
        paymentDTO.setRideId(1);
        paymentDTO.setCustomerEmail("serfezev@gmail.com");
        HttpEntity<PaymentDTO> entity = new HttpEntity<>(paymentDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/api/reservation/cancelPayment", entity, SuccessResponseDTO.class);

        assertNotEquals(response.getStatusCode(), HttpStatus.OK);
    }

    

}
