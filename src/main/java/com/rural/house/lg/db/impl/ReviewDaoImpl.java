package com.rural.house.lg.db.impl;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.rural.house.lg.config.MongoDbConf;
import com.rural.house.lg.db.ReviewDao;
import com.rural.house.lg.model.defaults.DefaultReview;
import com.rural.house.lg.model.interfaces.Review;
import org.bson.Document;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ReviewDaoImpl implements ReviewDao{

    private static final String COLLECTION_NAME = "review";

    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public ReviewDaoImpl(MongoClient mongoClient, MongoDbConf conf){
        this.database= mongoClient.getDatabase(conf.getDatabase());
        this.collection = database.getCollection(COLLECTION_NAME);
    }

    @Override
    public List<Review> getReviewList() {
        MongoCursor<Document> cursor = collection.find().iterator();

        Iterable<Document> iterable = () -> cursor;
        Stream<Document> targetStream = StreamSupport.stream(iterable.spliterator(), false);

        return targetStream.map(document -> {
            Review review = new DefaultReview();

            review.setDate(new Timestamp(document.getDate("date").getTime()));
            review.setName(document.getString("name"));
            review.setCity(document.getString("city"));
            review.setCountry(document.getString("country"));
            review.setComment(document.getString("comment"));

            return review;
            }
        ).collect(Collectors.toList());
    }

    @Override
    public void saveReview(Review review) {

    }
}
