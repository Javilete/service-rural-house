package com.rural.house.lg.service;

import com.rural.house.lg.dao.BookingDao;
import com.rural.house.lg.helpers.AvailabilityCalculator;
import com.rural.house.lg.model.Booking;
import com.rural.house.lg.model.Enquiry;
import com.rural.house.lg.model.Floor;
import com.rural.house.lg.model.RoomType;
import com.rural.house.lg.model.defaults.DefaultRoom;
import com.rural.house.lg.model.interfaces.Room;
import com.rural.house.lg.service.impl.BookingServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookingServiceImplShould {

    @Mock
    AvailabilityCalculator calculator;

    @Mock
    BookingDao dao;

    List<Room> rooms;

    BookingService service;

    @Before
    public void setUp() throws Exception {
        Room room = new DefaultRoom();
        room.setFloor(Floor.FIRST);
        room.setType(RoomType.DOUBLE);
        rooms = new ArrayList<>();
        rooms.add(room);

        service = new BookingServiceImpl(dao, calculator, rooms);
    }

    @Test
    public void callDaoLayerToRetrieveBookingOfThoseDates() {
        LocalDate arrivalDate = LocalDate.of(2017, Month.AUGUST, 10);
        LocalDate departureDate = LocalDate.of(2017, Month.AUGUST, 13);

        Enquiry enquiry = new Enquiry();
        enquiry.setArrivalDate(arrivalDate);
        enquiry.setDepartureDate(departureDate);

        service.getRoomAvailability(enquiry);

        verify(dao).getBookingsFrom(eq(arrivalDate), eq(departureDate));
    }

    @Test
    public void returnListOfAvailableRoomsForThoseDate() {
        LocalDate arrivalDate = LocalDate.of(2017, Month.AUGUST, 10);
        LocalDate departureDate = LocalDate.of(2017, Month.AUGUST, 13);

        Enquiry enquiry = new Enquiry();
        enquiry.setArrivalDate(arrivalDate);
        enquiry.setDepartureDate(departureDate);
        Room room = new DefaultRoom(RoomType.DOUBLE, Floor.FIRST);

        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking(room, 2, LocalDate.of(2017, Month.AUGUST, 11)));
        bookings.add(new Booking(room, 2, LocalDate.of(2017, Month.AUGUST, 12)));

        when(dao.getBookingsFrom(eq(arrivalDate), eq(departureDate)))
                .thenReturn(bookings);

        service.getRoomAvailability(enquiry);

        verify(calculator).calculate(eq(room), eq(enquiry), eq(bookings));
    }
}
