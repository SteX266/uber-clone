package com.backend.uberclone.rest;


import com.backend.uberclone.dto.PaymentDTO;
import com.backend.uberclone.dto.SuccessResponseDTO;
import com.backend.uberclone.dto.UserCredentialsDTO;
import com.backend.uberclone.dto.UserTokenState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.testng.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        locations = {"classpath:application.properties", "classpath:application-driver-not-available.properties"}
)
public class ConfirmPaymentDriverNotAvailableTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;
    public void setUp() {
        System.out.println("USAO I OVDE");
        UserCredentialsDTO requestTestDriver = new UserCredentialsDTO();
        requestTestDriver.setEmail("serfezev@gmail.com");
        requestTestDriver.setPassword("perica00");
        System.out.println("USAO DO OVDE");
        headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Access-Control-Allow-Origin", "*");
        HttpEntity<UserCredentialsDTO> httpEntity = new HttpEntity<>(requestTestDriver, headers);
        ResponseEntity<UserTokenState> resTest = restTemplate
                .exchange("/auth/login", HttpMethod.POST, httpEntity, UserTokenState.class);
        String tokenTestDriver = "Bearer " + resTest.getBody().getAccessToken();
        System.out.println("KURCU GLUPI");
        System.out.println(tokenTestDriver);
        headers = new HttpHeaders();
        headers.add("Authorization", tokenTestDriver);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content-Type", "application/json");
    }
    @Test
    public void testConfirmPaymentDriverNotAvailable() throws Exception {
        setUp();
        // this.reservationService.makeReservation(new ReservationDTO(new ArrayList<String>(Arrays.asList("Puskinova 11, Novi Sad", "Gogoljeva 23, Novi Sad")), LocalDateTime.now(), new ArrayList<>(Arrays.asList("Route1")), new ArrayList<>(Arrays.asList("serfezev@gmail.com")), VehicleType.ANY, ReservationType.INSTANT, false, false, 0.34, 80, 130, new ArrayList<>(Arrays.asList(19.4, 45.6)), new ArrayList<>(Arrays.asList(19.2, 45.7))));
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setReservationId(2);
        paymentDTO.setCanceled(false);
        paymentDTO.setAmount(100);
        paymentDTO.setCustomerEmail("serfezev@gmail.com");
        HttpEntity<PaymentDTO> entity = new HttpEntity<>(paymentDTO, headers);

        ResponseEntity<SuccessResponseDTO> response = restTemplate.postForEntity("/reservation/confirmPayment", entity, SuccessResponseDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
