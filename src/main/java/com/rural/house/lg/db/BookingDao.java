package com.rural.house.lg.db;


import org.bson.Document;

import java.sql.Timestamp;
import java.util.List;

public interface BookingDao {

    List<Document> getRoomAvailibilityList(Timestamp arrivingDate, Timestamp departingDate);

}