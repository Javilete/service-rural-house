package com.rural.house.lg.db;


import com.rural.house.lg.model.interfaces.BookingConfirmation;
import org.bson.Document;

import java.sql.Timestamp;
import java.util.List;

public interface BookingDao {

    List<Document> getRoomAvailibilityList(Timestamp arrivingDate, Timestamp departingDate);

    void saveBooking(List<BookingConfirmation> bookingConfirmation);
}