package com.chernybro.RubleMood.controller;

import com.chernybro.RubleMood.model.Rate;
import com.chernybro.RubleMood.service.GifService;
import com.chernybro.RubleMood.service.RatesService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AppController {

    private final RatesService ratesService;
    private final GifService gifService;
    @Value("${rates.base}")
    private String base;


    @Autowired
    public AppController(RatesService ratesService, GifService gifService) {
        this.ratesService = ratesService;
        this.gifService = gifService;
    }

    @GetMapping("/")
    public String mainPage(Model model){
        return "default_page";
    }

    @GetMapping("/currency-mood")
    public Map<String, String> takeGif(@RequestParam("code") String code){
        ratesService.updateAllRates();
        Double cur = ratesService.getCurrentRates(code);
        Double yest = ratesService.getYesterdayRates(code);
        String mood = ratesService.calculateTagOfMood(yest,cur);

        Map<String,String> map = new HashMap<>();
        map.put("currency", code);
        map.put("mood", mood);
        map.put("yesterday-rate", yest.toString());
        map.put("today-rate", cur.toString());
        map.put("base", base);
        map.put("url", gifService.getGif(mood).getStdUrl());

        return map;
    }

}