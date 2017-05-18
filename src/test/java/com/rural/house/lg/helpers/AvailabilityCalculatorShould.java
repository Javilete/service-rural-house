package com.rural.house.lg.helpers;

import com.google.common.collect.Lists;
import com.rural.house.lg.model.Booking;
import com.rural.house.lg.model.Enquiry;
import com.rural.house.lg.model.Floor;
import com.rural.house.lg.model.RoomType;
import com.rural.house.lg.model.defaults.DefaultAvailability;
import com.rural.house.lg.model.defaults.DefaultAvailableRoomResponse;
import com.rural.house.lg.model.defaults.DefaultRoom;
import com.rural.house.lg.model.interfaces.Availability;
import com.rural.house.lg.model.interfaces.AvailableRoomResponse;
import com.rural.house.lg.model.interfaces.Room;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AvailabilityCalculatorShould {

    private AvailabilityCalculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new AvailabilityCalculator();
    }

    @Test
    public void shouldReturnAvailableResponseForAroom() {
        Enquiry enquiry = new Enquiry();
        enquiry.setArrivalDate(LocalDate.of(2017, Month.AUGUST, 10));
        enquiry.setDepartureDate(LocalDate.of(2017, Month.AUGUST, 13));
        Room room = new DefaultRoom(RoomType.DOUBLE, Floor.FIRST);

        Booking bookingTenth = new Booking(room, 2, LocalDate.of(2017, Month.AUGUST, 10));
        Booking bookingEleventh = new Booking(room, 2, LocalDate.of(2017, Month.AUGUST, 11));
        List<Booking> bookings = Lists.newArrayList(bookingTenth, bookingEleventh);

        AvailableRoomResponse availability = calculator.calculate(room, enquiry, bookings);

        assertThat(availability, is(createExpectedAvailability(room)));
    }

    private AvailableRoomResponse createExpectedAvailability(Room room) {
        AvailableRoomResponse response = new DefaultAvailableRoomResponse();

        response.setRoom(room);
        response.setAvailability(Lists.newArrayList(
                new DefaultAvailability(LocalDate.of(2017, Month.AUGUST, 10), 0),
                new DefaultAvailability(LocalDate.of(2017, Month.AUGUST, 11), 0),
                new DefaultAvailability(LocalDate.of(2017, Month.AUGUST, 12), 2),
                new DefaultAvailability(LocalDate.of(2017, Month.AUGUST, 13), 2)));

        return response;
    }
}
