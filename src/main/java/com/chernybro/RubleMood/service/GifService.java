package com.chernybro.RubleMood.service;

import com.chernybro.RubleMood.client.GifsClient;
import com.chernybro.RubleMood.model.Gif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GifService {
    private GifsClient gifsClient;
    @Value("${giphy.apikey}")
    private String apikey;
    @Value("${giphy.random.bound}")
    private int bound;

    @Autowired
    public GifService(GifsClient gifsClient) {
        this.gifsClient = gifsClient;
    }


    // Required Unit Test (json gif contains url)
    public Gif getGif(String tag) {
        return gifsClient.getGif(apikey, tag, bound,0,"g","en");
    }
}
