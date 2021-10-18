package com.chernybro.RubleMood.controller;

import com.chernybro.RubleMood.model.Rate;
import com.chernybro.RubleMood.service.GifService;
import com.chernybro.RubleMood.service.RatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
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
    public String showGif(@RequestParam("code") String code, Model model){
        ratesService.updateAllRates();
        model.addAttribute("list",ratesService.getCurrencyCodes());
        model.addAttribute("yesterdayRates","1 " + base + " = " + ratesService.getYesterdayRates().getRates().get(code) + " " + code);
        model.addAttribute("currentRates","1 " + base + " = " + ratesService.getCurrentRates().getRates().get(code)  + " " + code);
        model.addAttribute("gif", gifService.getGif(ratesService.calculateTagOfMood(code)).getStdUrl());
        return "currency-mood";
    }

}