package com.chernybro.RubleMood.service;

import com.chernybro.RubleMood.client.RatesClient;
import com.chernybro.RubleMood.model.Gif;
import com.chernybro.RubleMood.model.Rate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatesService {
    private Rate yesterdayRates;
    private Rate currentRates;
    private RatesClient ratesClient;
    @Value("${exchangerates.appid}")
    private String appId;
    @Value("${exchangerates.base}")
    private String base;
    @Value("${giphy.richtag}")
    private String richTag;
    @Value("${giphy.broketag}")
    private String brokeTag;

    private Rate getCurrentRates() {
        return currentRates = ratesClient.getRates(appId, base);
    }

    public List<String> getCurrencyCodes() {
        List<String> res = null;
        if (getCurrentRates() != null) {
            res = new ArrayList<>(currentRates.getRates().keySet());
        }
        return res;
    }

    public String calculateTagOfMood(String code) {
        Double curValue;
        Double prevValue;
        curValue = currentRates.getRates().get(code);
        prevValue = yesterdayRates.getRates().get(code);
        double compareResult = Double.compare(curValue, prevValue);

        String tag = "";
        if (compareResult >= 0) {
            tag = this.richTag;
        } else if (compareResult < 0) {
            tag = this.brokeTag;
        }
        return tag;
        // Gif gif = giphyService.getGif(tag);
        // return gifObject;
    }
}
