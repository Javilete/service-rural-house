package com.rural.house.lg.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MongoDbConf {

    @JsonProperty
    private String url;

    @JsonProperty
    private Integer port;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
