package com.chernybro.RubleMood.Mock;

import com.github.tomakehurst.wiremock.*;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.impl.conn.Wire;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


public class GiphyWireMock {

    private static final int PORT = 8080;
    private static final String HOST = "localhost";

    private static WireMockServer server;

    @BeforeAll
    public static void setup() {
        server = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8080));
        server.start();
        ResponseDefinitionBuilder mockResponse = new ResponseDefinitionBuilder();
        mockResponse.withStatus(200);
               // .withBody("[{\"BrandName\":\"Alienware\",\"Features\":{\"Feature\":[\"8th Generation Intel® Core™ i5-8300H\",\"Windows 10 Home 64-bit English\",\"NVIDIA® GeForce® GTX 1660 Ti 6GB GDDR6\",\"8GB, 2x4GB, DDR4, 2666MHz\"]},\"Id\":1,\"LaptopName\":\"Alienware M17\"}]")
               // .withHeader("Content-Type", "application/json");


        stubFor(
                get("/currency-mood")
                        .willReturn(mockResponse)
        );
    }
    //@Test
    public void TestWM() {
        System.out.println(server.baseUrl());
        assertTrue(server.isRunning());
    }

    @Test
    public void TestGetEndPoint() throws InterruptedException {
        System.out.println("ewrewr");
        if (server.isRunning()) {
            Response response = RestAssured.
                    given().
                        get("http://localhost:8080/currency-mood").
                    then().
                        statusCode(200)
                        .extract().response();

            Assert.assertEquals(response.getStatusCode(), 200);
        }
    }

    @AfterAll
    public static void teardown() {
        if(null != server && server.isRunning()){
           server.shutdownServer();
        }
    }
}