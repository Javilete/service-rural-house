package com.rural.house.lg.dao;


import com.rural.house.lg.model.Booking;
import com.rural.house.lg.model.interfaces.BookingConfirmation;

import java.time.LocalDate;
import java.util.List;

public interface BookingDao {

    List<Booking> getBookingsFrom(LocalDate arrivalDate, LocalDate departureDate);

    void saveBooking(List<BookingConfirmation> bookingConfirmation);
}