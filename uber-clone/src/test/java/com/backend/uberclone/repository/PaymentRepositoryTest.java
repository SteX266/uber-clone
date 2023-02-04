package com.backend.uberclone.repository;

import com.backend.uberclone.model.Payment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void savePayment(){
        Payment p = new Payment();
        p.setPaid(true);
        p.setAmount(266);
        Payment newPayment = paymentRepository.save(p);
        Assertions.assertNotNull(newPayment);
        Assertions.assertEquals(266,newPayment.getAmount());
        Assertions.assertTrue(newPayment.isPaid());
    }
}
