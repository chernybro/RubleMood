package com.chernybro.RubleMood.model;

public class Gif {
    private String type;
    private String id;
    private String url;
    private String embed_url;
    private String title;
    private String stdUrl;

    public String getStdUrl() {
        return stdUrl;
    }

    public void setStdUrl(String stdUrl) {
        this.stdUrl = stdUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmbed_url() {
        return embed_url;
    }

    public void setEmbed_url(String embed_url) {
        this.embed_url = embed_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
