package com.chernybro.RubleMood.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;
import java.util.Random;


public class Gif {
    private String stdUrl;

    public String getStdUrl() {
        return stdUrl;
    }

    public void setStdUrl(String stdUrl) {
        this.stdUrl = stdUrl;
    }


    @Override
    public String toString() {
        return "Gif{" +
                ", stdUrl='" + stdUrl + '\'' +
                '}';
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("data")
    private void unpackNested(List<Map<String, Object>> data) {
        Map<String,Object> images = (Map<String, Object>)data.get(new Random().nextInt(data.size())).get("images");
        Map<String,Object> original = (Map<String, Object>)images.get("original");
        this.stdUrl = (String)original.get("url");
    }
}
