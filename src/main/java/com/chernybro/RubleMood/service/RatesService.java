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


    public Double getYesterdayRates(String code) {
        return yesterdayRates.getRates().get(code);
    }

    public Double getCurrentRates(String code) {
        return currentRates.getRates().get(code);
    }

    private void updateCurrentRates(){
        currentRates = ratesClient.getCurrentRates(appId, base);
    }

    public void updateAllRates() {
        updateCurrentRates();
        updateYesterdayRates();
    }

    public List<String> getCurrencyCodes() {
        List<String> res = null;
        if (currentRates != null) {
            res = new ArrayList<>(currentRates.getRates().keySet());
        }
        return res;
    }

    public void updateYesterdayRates() {
        String prevDate = getYesterdayDate();
        yesterdayRates = ratesClient.getYesterdayRates(prevDate, appId, base);
    }

    private String getYesterdayDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.DATE, -1);
        return sFormat.format(cal.getTime());
    }

    // Required Unit Test (check right mood tag)
    public String calculateTagOfMood(Double yest, Double curr) {
        int compareResult = Double.compare(curr, yest);
        return  (compareResult >= 0) ? this.richTag : this.brokeTag;
    }
}
