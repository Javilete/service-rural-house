package com.rural.house.lg.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MongoDbConf {

    @JsonProperty
    private String uri;

    @JsonProperty
    private String database;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
