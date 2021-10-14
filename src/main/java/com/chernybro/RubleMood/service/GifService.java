package com.chernybro.RubleMood.service;

import com.chernybro.RubleMood.client.GifsClient;
import com.chernybro.RubleMood.model.Gif;
import org.springframework.beans.factory.annotation.Value;

public class GifService {
    private GifsClient gifsClient;
    @Value("${giphy.apikey}")
    private String apikey;


    public GifService(GifsClient gifsClient) {
        this.gifsClient = gifsClient;
    }

    private Gif getGif(String tag) {
        return gifsClient.getGif(this.apikey, tag);
    }
}
