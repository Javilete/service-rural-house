package com.rural.house.lg.resources;

import com.rural.house.lg.model.Enquiry;
import com.rural.house.lg.model.defaults.DefaultEnquiryRequest;
import com.rural.house.lg.model.interfaces.EnquiryRequest;
import com.rural.house.lg.resource.BookingResource;
import com.rural.house.lg.service.BookingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import javax.ws.rs.WebApplicationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZonedDateTime;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookingResourceImplShould {

    @Mock
    BookingService bookingService;

    @Mock
    ModelMapper modelMapper;

    BookingResource bookingResource;

    @Before
    public void setUp() throws Exception {
        bookingResource = new BookingResourceImpl(bookingService, modelMapper);
    }

    @Test
    public void checkForAvailabilityCallingService() {
        EnquiryRequest bookingEnquiry = createEnquiryRequest();

        Enquiry enquiry = new Enquiry();
        enquiry.setArrivalDate(LocalDate.of(2017, Month.AUGUST, 10));
        enquiry.setDepartureDate(LocalDate.of(2017, Month.AUGUST, 5));
        enquiry.setGuests(4);

        when(modelMapper.map(any(EnquiryRequest.class), any(Class.class))).thenReturn(enquiry);

        bookingResource.checkReservationImpl(bookingEnquiry);

        verify(bookingService).getRoomAvailability(eq(enquiry));
    }

    @Test(expected = WebApplicationException.class)
    public void throwWebExceptionWhenTheServiceCallReturnsAnError() {
        EnquiryRequest bookingEnquiry = createEnquiryRequest();
        when(modelMapper.map(any(EnquiryRequest.class), any(Class.class))).thenReturn(new Enquiry());
        when(bookingService.getRoomAvailability(any(Enquiry.class))).thenThrow(Exception.class);

        bookingResource.checkReservationImpl(bookingEnquiry);
    }

    private EnquiryRequest createEnquiryRequest() {
        EnquiryRequest bookingEnquiry = new DefaultEnquiryRequest();
        bookingEnquiry.setArrivalDate(LocalDate.of(2017, Month.AUGUST, 10));
        bookingEnquiry.setDepartureDate(LocalDate.of(2017, Month.AUGUST, 5));
        bookingEnquiry.setGuests(4);
        return bookingEnquiry;
    }
}
