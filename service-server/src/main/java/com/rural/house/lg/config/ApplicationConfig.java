package com.rural.house.lg.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rural.house.lg.model.interfaces.Room;

import java.util.List;

public class ApplicationConfig {

    @JsonProperty
    private String applicationVersionFilePath;

    @JsonProperty
    private MongoDbConf mongoDbConf;

    @JsonProperty
    private List<Room> rooms;

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

    public List<Room> getRooms(){
        return this.rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
