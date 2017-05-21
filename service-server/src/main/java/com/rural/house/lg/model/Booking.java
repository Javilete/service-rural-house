package com.rural.house.lg.model;

import com.rural.house.lg.model.interfaces.Room;

import java.time.LocalDate;

public class Booking {

    private final Room room;

    private final int guests;

    private final LocalDate date;

    public Booking(Room room, int guests, LocalDate date) {
        this.room = room;
        this.guests = guests;
        this.date = date;
    }

    public int getGuests() {
        return guests;
    }

    public LocalDate getDate() {
        return date;
    }
}
