package com.rural.house.lg.service;


import com.rural.house.lg.model.interfaces.AvailableRoomResponse;

import java.sql.Timestamp;
import java.util.List;

public interface BookingService {

    List<AvailableRoomResponse> retrieveRoomAvailability(Timestamp arrivingDate, Timestamp departingDate, Integer guests);
}
