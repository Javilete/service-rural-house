package com.rural.house.lg.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.rural.house.lg.db.BookingDao;
import com.rural.house.lg.model.RoomType;
import com.rural.house.lg.model.defaults.DefaultAvailableRoomResponse;
import com.rural.house.lg.model.interfaces.AvailableRoomResponse;
import com.rural.house.lg.service.BookingService;
import org.bson.Document;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.List;

public class BookingServiceImpl implements BookingService {

    private static final String ROOM_TYPE_KEY = "roomType";
    private static final String GUESTS_KEY = "guests";

    private BookingDao bookingDao;

    public BookingServiceImpl(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    @Override
    public List<AvailableRoomResponse> getRoomAvailability(final Timestamp arrivingDate, final Timestamp departingDate, Integer guests) {

        List<Document> availibilityList = bookingDao.getRoomAvailibilityList(arrivingDate, departingDate);

        return Lists.transform(availibilityList, new Function<Document, AvailableRoomResponse>() {
            @Nullable
            @Override
            public AvailableRoomResponse apply(@Nullable Document input) {
                return transformDocument(input, arrivingDate, departingDate);
            }
        });
    }

    private AvailableRoomResponse transformDocument(Document input, Timestamp arrivingDate, Timestamp departingDate){
        AvailableRoomResponse availableRoomResponse = new DefaultAvailableRoomResponse();

        availableRoomResponse.setType(RoomType.valueOf(input.getString(ROOM_TYPE_KEY)));
        availableRoomResponse.setGuests(input.getInteger(GUESTS_KEY));
        availableRoomResponse.setDateList(createListOfDates(arrivingDate, departingDate));

        return availableRoomResponse;
    }

    private List<Timestamp> createListOfDates(Timestamp arrivingDate, Timestamp departingDate){

        List<Timestamp> datesList = Lists.newArrayList();

        return null;
    }
}
