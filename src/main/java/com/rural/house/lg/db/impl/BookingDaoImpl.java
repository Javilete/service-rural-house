package com.rural.house.lg.db.impl;


import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.rural.house.lg.db.BookingDao;
import com.rural.house.lg.model.interfaces.BookingConfirmation;
import org.bson.Document;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class BookingDaoImpl implements BookingDao {

    private static final String COLLECTION_NAME = "booking";

    private MongoCollection<Document> collection;

    public BookingDaoImpl(MongoDatabase database){
        this.collection = database.getCollection(COLLECTION_NAME);
    }

    public List<Document> getRoomAvailibilityList(final Timestamp arrivingDate, final Timestamp departingDate){

        List<Document> result = Lists.newArrayList();
        MongoCursor<Document> cursor = collection.find(and(gte("date", new Date(arrivingDate.getTime())), lte("date", new Date(departingDate.getTime())))).iterator();

        try {
            while (cursor.hasNext()) {
               result.add(cursor.next());
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    @Override
    public void saveBooking(final List<BookingConfirmation> bookingConfirmationList) {

        List<Document> bookingDocumentsList = Lists.transform(bookingConfirmationList, new Function<BookingConfirmation, Document>() {
            @Nullable
            @Override
            public Document apply(@Nullable BookingConfirmation input) {
                return new Document()
                        .append("date", input.getDate())
                        .append("roomType", input.getRoomType())
                        .append("guests", input.getGuests());
            }
        });

        collection.insertMany(bookingDocumentsList);
    }
}
