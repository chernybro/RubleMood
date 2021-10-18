package com.chernybro.RubleMood.service;

import com.chernybro.RubleMood.client.RatesClient;
import com.chernybro.RubleMood.model.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class RatesService {
    private Rate yesterdayRates;
    private Rate currentRates;
    @Autowired
    private RatesClient ratesClient;

    @Value("${rates.appid}")
    private String appId;

    @Value("${rates.base}")
    private String base;

    @Value("${giphy.richtag}")
    private String richTag;

    @Value("${giphy.broketag}")
    private String brokeTag;


    public Rate getYesterdayRates() {
        return yesterdayRates;
    }

    public Rate getCurrentRates() {
        return currentRates;
    }

    private Rate updateCurrentRates() {
        return currentRates = ratesClient.getCurrentRates(appId, base);
    }

    public Rate updateAllRates() {
        Long curTime = System.currentTimeMillis();
        updateCurrentRates();
        updateYesterdayRates(curTime);
        return currentRates;

    }

    public List<String> getCurrencyCodes() {
        List<String> res = null;
        if (updateCurrentRates() != null) {
            res = new ArrayList<>(currentRates.getRates().keySet());
        }
        return res;
    }

    public Rate updateYesterdayRates(Long curTime) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
        cal.setTimeInMillis(curTime);
        cal.add(Calendar.DATE, -1);
        String prevDate = sFormat.format(cal.getTime());

        return yesterdayRates = ratesClient.getYesterdayRates(prevDate, appId, base);
    }

    public String calculateTagOfMood(String code) {
        Double curValue;
        Double prevValue;
        curValue = currentRates.getRates().get(code);
        prevValue = yesterdayRates.getRates().get(code);
        int compareResult = Double.compare(curValue, prevValue);

        String tag = "";
        if (compareResult >= 0) {
            tag = this.richTag;
        } else {
            tag = this.brokeTag;
        }
        return tag;
        // Gif gif = giphyService.getGif(tag);
        // return gifObject;
    }
}
