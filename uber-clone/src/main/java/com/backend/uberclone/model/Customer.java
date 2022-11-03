package com.backend.uberclone.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Customer extends User {
    private List<PaymentDetails> paymentDetails;
    private List<Route> favoriteRoutes;

}
