package com.rural.house.lg.db;


import com.mongodb.MongoClient;
import com.rural.house.lg.config.MongoDbConf;

public class BookingDao {

    private MongoClient mongoClient;

    public BookingDao(MongoDbConf conf){
        this.mongoClient = new MongoClient(conf.getUrl(), conf.getPort());
    }



}
