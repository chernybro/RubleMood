package com.chernybro.RubleMood.testMockedExternalApi;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class GiphyWireMock {

    private static final int PORT = 8080;
    private static final String HOST = "localhost";

    private static WireMockServer server;

    @BeforeAll
    public static void setup() {
        server = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8080));
        server.start();
        ResponseDefinitionBuilder mockResponse = new ResponseDefinitionBuilder();
        mockResponse
                .withBodyFile("json\\giphy.json");
        stubFor(
                get("/gif")
                        .willReturn(mockResponse)
        );
    }
    @Test
    public void TestWM() throws InterruptedException {
        System.out.println(server.baseUrl());
        assertTrue(server.isRunning());
        //Thread.sleep(99999);
    }

    @Test
    public void checkGifApiSuccessRequest() throws JSONException {
        Response response = RestAssured.
                given().
                    get("http://localhost:8080/gif").
                then().
                    extract().response();
        String json = response.print();
        JSONObject request = new JSONObject(json);
        String result = request.getJSONArray("data").getJSONObject(0).getJSONObject("images").getJSONObject("original").getString("url");
        Assert.assertNotEquals(result, null);
    }


    @AfterAll
    public static void teardown() {
        if(null != server && server.isRunning()){
           server.shutdownServer();
        }
    }
}