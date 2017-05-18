package com.rural.house.lg.dao.impl;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.rural.house.lg.model.Booking;
import org.bson.conversions.Bson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.Month;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookingDaoImplShould {

    @Mock
    private MongoCollection collection;

    @Mock
    private MongoDatabase database;

    @Mock
    private FindIterable iterables;

    private BookingDaoImpl bookingDao;

    @Before
    public void setUp() throws Exception {
        when(database.getCollection(eq("booking"), eq(Booking.class))).thenReturn(collection);
        bookingDao = new BookingDaoImpl(database);
    }

    @Test
    public void callFindMethodToRetreiveBookings() {
        LocalDate from = LocalDate.of(2017, Month.AUGUST, 10);
        LocalDate to = LocalDate.of(2017, Month.AUGUST, 15);
        when(collection.find(any(Bson.class))).thenReturn(iterables);

        bookingDao.getBookingsFrom(from, to);

        verify(collection).find(any(Bson.class));
    }
}
