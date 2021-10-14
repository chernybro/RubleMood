package com.chernybro.RubleMood.client;

import com.chernybro.RubleMood.model.Gif;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "giphy", url = "${giphy.url}")
public interface GifsClient {

    @GetMapping("/random")
    Gif getGif(
            @RequestParam("api_key") String api_key,
            @RequestParam("tag") String tag);
}
