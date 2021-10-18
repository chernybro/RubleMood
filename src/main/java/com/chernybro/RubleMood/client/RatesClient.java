package com.chernybro.RubleMood.client;

import com.chernybro.RubleMood.model.Rate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "rates-client", url = "${rates.url}")
public interface RatesClient {
    @GetMapping("/latest.json")
    Rate getCurrentRates(
            @RequestParam("app_id") String appId,
            @RequestParam("base") String base);

    @GetMapping("/historical/{date}.json")
    Rate getYesterdayRates(
            @PathVariable("date") String prevDate,
            @RequestParam("app_id") String appId,
            @RequestParam("base") String base);
}