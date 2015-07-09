package com.rural.house.lg.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rural.house.lg.db.BookingDao;
import com.rural.house.lg.model.Floor;
import com.rural.house.lg.model.RoomType;
import com.rural.house.lg.model.defaults.DefaultAvailability;
import com.rural.house.lg.model.defaults.DefaultAvailableRoomResponse;
import com.rural.house.lg.model.defaults.DefaultRoom;
import com.rural.house.lg.model.interfaces.Availability;
import com.rural.house.lg.model.interfaces.AvailableRoomResponse;
import com.rural.house.lg.model.interfaces.BookingConfirmation;
import com.rural.house.lg.model.interfaces.Room;
import com.rural.house.lg.service.BookingService;
import org.bson.Document;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookingServiceImpl implements BookingService {

    private static Logger LOGGER = LoggerFactory.getLogger(BookingServiceImpl.class);

    private static final Integer MAX_GUESTS = 2;

    private static final String ROOM_TYPE_KEY = "type";
    private static final String FLOOR_KEY = "floor";
    private static final String GUESTS_KEY = "guests";
    private static final String DATE_KEY = "date";

    private BookingDao bookingDao;

    private List<Room> rooms;

    public BookingServiceImpl(BookingDao bookingDao, List<Room> rooms) {
        this.bookingDao = bookingDao;
        this.rooms = rooms;
    }

    @Override
    public List<AvailableRoomResponse> getRoomAvailability(final Timestamp arrivingDate, final Timestamp departingDate, Integer guests) {

        List<Document> bookedList = bookingDao.getRoomAvailibilityList(arrivingDate, departingDate);



        //Create availability list
        Map<Room, AvailableRoomResponse> defaultAvailabilityMap = createDefaultMapOfRoomAvailability(arrivingDate, departingDate);

        if(bookedList.size() > 0){
            for(Document bookedDoc: bookedList){
                //Create room object to retrieve
                Room room = new DefaultRoom(RoomType.valueOf(bookedDoc.getString(ROOM_TYPE_KEY)),
                        Floor.valueOf(bookedDoc.getString(FLOOR_KEY)));
                //Get availableRoomResponse object
                AvailableRoomResponse avr = defaultAvailabilityMap.get(room);

                avr.getAvailability().stream()
                        .filter((av) -> {
                            DateTime avDateTime = new DateTime(av.getDate());
                            DateTime bookedDocDateTime = new DateTime(bookedDoc.getDate(DATE_KEY));
                            return avDateTime.toLocalDate().isEqual(bookedDocDateTime.toLocalDate());
                        })
                        .forEach(av -> av.setGuests(av.getGuests() - bookedDoc.get(GUESTS_KEY,Integer.class)));
                        //Maybe with map
            }
        }

        return new ArrayList<>(defaultAvailabilityMap.values());
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

    private Map<Room, AvailableRoomResponse> createDefaultMapOfRoomAvailability(final Timestamp arrivingDate, final Timestamp departingDate) {
        Map<Room, AvailableRoomResponse> availabilityDefaultMap = Maps.newHashMap();
        for(Room room: this.rooms){
            AvailableRoomResponse availableRoomResponse = new DefaultAvailableRoomResponse();
            availableRoomResponse.setRoom(room);
            availableRoomResponse.setAvailability(createListOfAvailability(arrivingDate, departingDate));
            availabilityDefaultMap.put(room, availableRoomResponse);
        }

        return availabilityDefaultMap;
    }

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

    private List<Availability> createListOfAvailability(final Timestamp arrivingDate, final Timestamp departingDate){

        List<Availability> datesList = Lists.newArrayList();

        DateTime start = new DateTime(arrivingDate);
        while (start.isBefore(departingDate.getTime())){
            DateTime dt = new DateTime(start);
            Availability a = new DefaultAvailability();
            a.setDate(new Timestamp(dt.getMillis()));
            a.setGuests(MAX_GUESTS);
            datesList.add(a);
            start = dt.plusDays(1);
        }

        return datesList;
    }
}
