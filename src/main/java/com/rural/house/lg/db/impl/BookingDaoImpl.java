package com.rural.house.lg.db.impl;


import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.rural.house.lg.config.MongoDbConf;
import com.rural.house.lg.db.BookingDao;
import org.bson.Document;

import java.sql.Timestamp;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class BookingDaoImpl implements BookingDao {

    private static final String DATABASE = "rh";
    private static final String COLLECTION = "booking";

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public BookingDaoImpl(MongoDbConf conf){
        this.mongoClient = new MongoClient(conf.getUrl(), conf.getPort());
        this.database= mongoClient.getDatabase(DATABASE);
        this.collection = database.getCollection(COLLECTION);
    }

    public List<Document> getRoomAvailibilityList(Timestamp arrivingDate, Timestamp departingDate){

        List<Document> result = Lists.newArrayList();
        MongoCursor<Document> cursor = collection.find(and(gt("date", arrivingDate), lte("date", departingDate))).iterator();

        try {
            while (cursor.hasNext()) {
               result.add(cursor.next());
            }
        } finally {
            cursor.close();
        }

        return result;
    }
}
