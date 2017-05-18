package com.rural.house.lg.helpers;

import com.google.common.collect.Lists;
import com.rural.house.lg.model.Booking;
import com.rural.house.lg.model.Enquiry;
import com.rural.house.lg.model.defaults.DefaultAvailability;
import com.rural.house.lg.model.defaults.DefaultAvailableRoomResponse;
import com.rural.house.lg.model.interfaces.Availability;
import com.rural.house.lg.model.interfaces.AvailableRoomResponse;
import com.rural.house.lg.model.interfaces.Room;

import java.time.LocalDate;
import java.util.List;

public class AvailabilityCalculator {

    private static final int TAKEN_ROOM = 0;
    private static final long ONE_DAY = 1L;
    private static final int DEFAULT_GUEST_NUMBER = 2;

    public AvailableRoomResponse calculate(Room room, Enquiry enquiry, List<Booking> bookings) {
        List<Availability> availabilities = createListOfAvailability(enquiry.getArrivalDate(), enquiry.getDepartureDate());
        updateAvailability(bookings, availabilities);

        return new DefaultAvailableRoomResponse(room, availabilities);
    }

    private void updateAvailability(List<Booking> bookings, List<Availability> availabilities) {
        for (Availability availability : availabilities) {
            for (Booking booking : bookings) {
                if (booking.getDate().isEqual(availability.getDate())) {
                    availability.setGuests(TAKEN_ROOM);
                }
            }
        }
    }

    private List<Availability> createListOfAvailability(LocalDate arrivalDate, LocalDate departureDate) {
        List<Availability> dates = Lists.newArrayList();

        LocalDate start = arrivalDate;
        while (start.isBefore(departureDate.plusDays(ONE_DAY))) {
            dates.add(new DefaultAvailability(start, DEFAULT_GUEST_NUMBER));
            start = start.plusDays(ONE_DAY);
        }

        return dates;
    }
}
