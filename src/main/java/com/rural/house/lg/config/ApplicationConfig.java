package com.rural.house.lg.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationConfig {

    @JsonProperty
    private String applicationVersionFilePath;

    @JsonProperty
    private MongoDbConf mongoDbConf;

    public String getApplicationVersionFilePath() {
        return applicationVersionFilePath;
    }

    public void setApplicationVersionFilePath(String applicationVersionFilePath) {
        this.applicationVersionFilePath = applicationVersionFilePath;
    }

    public MongoDbConf getMongoDbConf() {
        return mongoDbConf;
    }

    public void setMongoDbConf(MongoDbConf mongoDbConf) {
        this.mongoDbConf = mongoDbConf;
    }
}
