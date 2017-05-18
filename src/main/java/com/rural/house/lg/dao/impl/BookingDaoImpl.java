package com.rural.house.lg.dao.impl;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.rural.house.lg.dao.BookingDao;
import com.rural.house.lg.model.Booking;
import com.rural.house.lg.model.interfaces.BookingConfirmation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lte;

public class BookingDaoImpl implements BookingDao {

    private static final String COLLECTION_NAME = "booking";
    private static final String DATE_FIELD = "date";

    private MongoCollection<Booking> collection;

    public BookingDaoImpl(MongoDatabase database) {
        this.collection = database.getCollection(COLLECTION_NAME, Booking.class);
    }

    @Override
    public List<Booking> getBookingsFrom(LocalDate arrivalDate, LocalDate departureDate) {
        return collection.find(and(gte(DATE_FIELD, arrivalDate), lte(DATE_FIELD, departureDate)))
                .into(new ArrayList<>());
    }

    @Override
    public void saveBooking(final List<BookingConfirmation> bookingConfirmationList) {
        throw new UnsupportedOperationException();
    }
}
