package com.rural.house.lg.resources;

import com.rural.house.lg.model.Enquiry;
import com.rural.house.lg.model.interfaces.AvailableRoomResponse;
import com.rural.house.lg.model.interfaces.BookingConfirmation;
import com.rural.house.lg.model.interfaces.EnquiryRequest;
import com.rural.house.lg.resource.BookingResource;
import com.rural.house.lg.service.BookingService;
import io.dropwizard.jersey.errors.ErrorMessage;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Path("/api/booking")
public class BookingResourceImpl extends BookingResource {

    private static Logger LOGGER = LoggerFactory.getLogger(BookingResourceImpl.class);

    private ModelMapper modelMapper;
    private final BookingService bookingService;

    public BookingResourceImpl(BookingService bookingService, ModelMapper modelMapper) {
        this.bookingService = bookingService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response checkReservationImpl(EnquiryRequest bookingEnquiry) {

        List<AvailableRoomResponse> availableRoomResponses;
        Enquiry enquiry = modelMapper.map(bookingEnquiry, Enquiry.class);

        try {
            availableRoomResponses = bookingService.getRoomAvailability(enquiry);
        } catch (Exception e) {
            LOGGER.debug("Booking service - checkReservation - ERROR: when retriving bookings between the following dates: " +
                    bookingEnquiry.getArrivalDate().toString() + " and " + bookingEnquiry.getDepartureDate().toString());
            throw new WebApplicationException(Response.status(INTERNAL_SERVER_ERROR)
                    .entity(new ErrorMessage("Error when trying to get the booking"))
                    .build());
        }

        return Response.ok().entity(availableRoomResponses).build();
    }

    @Override
    public Response submitReservationImpl(List<BookingConfirmation> bookingConfirmation) {

        try {
            bookingService.confirmBookingDetails(bookingConfirmation);
        } catch (Exception e) {
            LOGGER.debug("Booking service - submitReservation - ERROR: when submiting a booking between: " +
                    bookingConfirmation.get(0).getDate().toString() + " and "
                    + bookingConfirmation.get(bookingConfirmation.size() - 1).getDate().toString());
            throw new WebApplicationException(Response.status(INTERNAL_SERVER_ERROR)
                    .entity(new ErrorMessage("Error when submiting the booking confirmation"))
                    .build());
        }

        return Response.ok().build();
    }
}
