package com.rural.house.lg.resources;

import com.rural.house.lg.db.BookingDao;
import com.rural.house.lg.model.interfaces.Booking;
import com.rural.house.lg.resource.BookingResource;
import org.eclipse.jetty.server.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;

@Path("/api/booking")
public class BookingResourceImpl extends BookingResource {

    private static Logger LOGGER = LoggerFactory.getLogger(BookingResourceImpl.class);

    private final BookingDao bookingDao;

    public BookingResourceImpl(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    @Override
    public Response checkReservationImpl(Booking booking) {
        return null;
    }

    @Override
    public Response submitReservationImpl(Booking booking) {
        return null;
    }
}
