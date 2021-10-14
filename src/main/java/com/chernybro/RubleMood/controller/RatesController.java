package com.chernybro.RubleMood.controller;

import com.chernybro.RubleMood.client.RatesClient;
import com.chernybro.RubleMood.model.Rate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatesController {

    private final RatesClient client;
    private Rate currentRates;

    public RatesController(RatesClient client) {
        this.client = client;
    }




}