package com.rural.house.lg.service.impl;

import com.rural.house.lg.dao.BookingDao;
import com.rural.house.lg.helpers.AvailabilityCalculator;
import com.rural.house.lg.model.Booking;
import com.rural.house.lg.model.Enquiry;
import com.rural.house.lg.model.interfaces.AvailableRoomResponse;
import com.rural.house.lg.model.interfaces.BookingConfirmation;
import com.rural.house.lg.model.interfaces.Room;
import com.rural.house.lg.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class BookingServiceImpl implements BookingService {

    private static Logger LOGGER = LoggerFactory.getLogger(BookingServiceImpl.class);

    private BookingDao bookingDao;
    private AvailabilityCalculator calculator;

    private List<Room> rooms;

    public BookingServiceImpl(BookingDao bookingDao, AvailabilityCalculator calculator, List<Room> rooms) {
        this.bookingDao = bookingDao;
        this.calculator = calculator;
        this.rooms = rooms;
    }

    @Override
    public List<AvailableRoomResponse> getRoomAvailability(Enquiry enquiry) {
        List<Booking> bookedList = bookingDao.getBookingsFrom(enquiry.getArrivalDate(), enquiry.getDepartureDate());

        List<AvailableRoomResponse> availability = rooms.stream()
                .map(room -> calculator.calculate(room, enquiry, bookedList))
                .collect(Collectors.toList());

        return availability;
    }

    @Override
    public void confirmBookingDetails(final List<BookingConfirmation> bookingConfirmation) {
        bookingDao.saveBooking(bookingConfirmation);
    }

    /**
     *
     * Creates a default map of room availability with the default values and between the search
     * boundary dates
     *
     * @param arrivingDate  date of arrival for the booking search
     * @param departingDate date of departing for the booking search
     * @return              A map having as key {@link Room} objects of the house retrieved from the configuration
     *                      files and value {@link AvailableRoomResponse} object for each room of the search date
     *                      provided
     *
     */

//    private Map<Room, AvailableRoomResponse> createDefaultMapOfRoomAvailability(final Timestamp arrivingDate, final Timestamp departingDate) {
//        Map<Room, AvailableRoomResponse> availabilityDefaultMap = Maps.newHashMap();
//        for(Room room: this.rooms){
//            AvailableRoomResponse availableRoomResponse = new DefaultAvailableRoomResponse();
//            availableRoomResponse.setRoom(room);
//            availableRoomResponse.setAvailability(createListOfAvailability(arrivingDate, departingDate));
//            availabilityDefaultMap.put(room, availableRoomResponse);
//        }
//
//        return availabilityDefaultMap;
//    }

    /**
     *
     * Creates a list of {@link com.rural.house.lg.model.interfaces.Availability} objects as default which will contain
     * the dates between the search was done and as default guest value per each room as 2
     *
     * @param arrivingDate  date of arrival for the booking search
     * @param departingDate date of departing for the booking search
     * @return              A default list of availability objects with the dates of the search and the default
     *                      guests set to 2
     */

//    private List<Availability> createListOfAvailability(final Timestamp arrivingDate, final Timestamp departingDate){
//
//        List<Availability> datesList = Lists.newArrayList();
//
//        DateTime start = new DateTime(arrivingDate);
//        while (start.isBefore(departingDate.getTime())){
//            DateTime dt = new DateTime(start);
//            Availability a = new DefaultAvailability();
//            a.setDate(LocalDate.of(dt.getMillis()));
//            a.setGuests(MAX_GUESTS);
//            datesList.add(a);
//            start = dt.plusDays(1);
//        }
//
//        return datesList;
//    }
}
