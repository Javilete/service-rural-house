package com.rural.house.lg.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MongoDbConf {

    @JsonProperty
    private String url;

    @JsonProperty
    private Integer port;

    @JsonProperty
    private String database;

    @JsonProperty
    private String collection;

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

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
}
