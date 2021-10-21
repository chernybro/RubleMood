package com.chernybro.RubleMood.controller;

import com.chernybro.RubleMood.service.GifService;
import com.chernybro.RubleMood.service.RatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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
    public String mainPage(){
        return "go to /currency-mood?code=RUB";
    }

    @GetMapping("/currency-mood")
    public ResponseEntity takeGif(@RequestParam("code") String code, HttpServletResponse response){
        ratesService.updateAllRates();
        Map<String,String> map = new HashMap<>();
        if (!ratesService.getCurrencyCodes().contains(code)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Double cur = ratesService.getCurrentRates(code);
            Double yest = ratesService.getYesterdayRates(code);
            String mood = ratesService.calculateTagOfMood(yest, cur);


            map.put("currency", code);
            map.put("mood", mood);
            map.put("yesterday-rate", yest.toString());
            map.put("today-rate", cur.toString());
            map.put("base", base);
            map.put("url", gifService.getGif(mood).getStdUrl());
        }
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

}