package com.chernybro.RubleMood;

import com.chernybro.RubleMood.service.RatesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;


@ExtendWith(SpringExtension.class)
public class RatesUnitTest {
    @MockBean
    private RatesService rateService;

    @Value("${giphy.richtag}")
    private String richTag;

    @Value("${giphy.broketag}")
    private String brokeTag;

    @Test
    void whenCurrentRatesMoreThanYesterdayReturnRich() {
        String code = "RUB";
        Double more = 100.3;
        Double less = 100.2;
        RatesService nonmockedService = new RatesService();
        ReflectionTestUtils.setField(nonmockedService, "richTag", "rich");
        ReflectionTestUtils.setField(nonmockedService, "brokeTag", "broke");

        BDDMockito.when(rateService.getCurrentRates(code)).thenReturn(more);
        BDDMockito.when(rateService.getYesterdayRates(code)).thenReturn(less);
        Assertions.assertEquals(
                nonmockedService.calculateTagOfMood(rateService.getYesterdayRates(code), rateService.getCurrentRates(code)),
                "rich");
    }

    @Test
    void whenCurrentRatesLessThanYesterdayReturnBroke() {
        String code = "RUB";
        Double more = 100.3;
        Double less = 100.2;
        RatesService nonmockedService = new RatesService();
        ReflectionTestUtils.setField(nonmockedService, "richTag", "rich");
        ReflectionTestUtils.setField(nonmockedService, "brokeTag", "broke");

        BDDMockito.when(rateService.getCurrentRates(code)).thenReturn(less);
        BDDMockito.when(rateService.getYesterdayRates(code)).thenReturn(more);
        Assertions.assertEquals(
                nonmockedService.calculateTagOfMood(rateService.getYesterdayRates(code), rateService.getCurrentRates(code)),
                "broke");
    }

}
