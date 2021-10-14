package com.chernybro.RubleMood.client;

import com.chernybro.RubleMood.model.Rate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "rates-client", url = "${rates.url}")
public interface RatesClient {

    @GetMapping("/latest.json")
    Rate getRates(
            String appId,
            String base);

    @GetMapping("/historical/{date}.json")
    Rate getYesterdayRates(
            String prevDate,
            String appId,
            String base);
}