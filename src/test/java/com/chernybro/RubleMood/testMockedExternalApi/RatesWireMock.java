package com.chernybro.RubleMood.testMockedExternalApi;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RatesWireMock {

    private static final int PORT = 8080;
    private static final String HOST = "localhost";

    private static WireMockServer server;

    @BeforeAll
    public static void setup() {
        server = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8080));
        server.start();
        ResponseDefinitionBuilder mockResponse = new ResponseDefinitionBuilder();
        mockResponse
                .withBodyFile("json\\rates.json");
        stubFor(
                get("/rates")
                        .willReturn(mockResponse)
        );
    }
    @Test
    public void TestWM() {
        System.out.println(server.baseUrl());
        assertTrue(server.isRunning());
        //Thread.sleep(99999);
    }

    @Test
    public void checkResponseHadRates() throws JSONException {
        Response response = RestAssured.
                given().
                get("http://localhost:8080/rates").
                then().
                extract().response();
        String json = response.print();
        JSONObject request = new JSONObject(json);
        String res = request.getJSONObject("rates").toString();
        Assertions.assertNotEquals(res, null);
    }


    @AfterAll
    public static void teardown() {
        if(null != server && server.isRunning()){
            server.shutdownServer();
        }
    }
}