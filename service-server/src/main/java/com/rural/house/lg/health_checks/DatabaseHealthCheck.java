package com.rural.house.lg.health_checks;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoClient;

public class DatabaseHealthCheck extends HealthCheck{

    private final MongoClient mongoClient;
    private final String db;

    public DatabaseHealthCheck(MongoClient mongoClient, String db){
        this.mongoClient = mongoClient;
        this.db = db;
    }

    @Override
    protected Result check() throws Exception {
        try{
            mongoClient.getDatabase(this.db);
            return Result.healthy("Mongo client connected to " + this.db + " database.");
        }catch(Exception e){
            return Result.unhealthy("Can not connect to mongoDb server, check it is up and running.");
        }
    }
}

