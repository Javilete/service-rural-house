package com.rural.house.lg.model;

import java.time.LocalDate;

public class Enquiry {

    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private int guests;

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public int getGuests() {
        return guests;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enquiry)) return false;

        Enquiry enquiry = (Enquiry) o;

        if (guests != enquiry.guests) return false;
        if (!arrivalDate.equals(enquiry.arrivalDate)) return false;
        return departureDate.equals(enquiry.departureDate);

    }

    @Override
    public int hashCode() {
        int result = arrivalDate.hashCode();
        result = 31 * result + departureDate.hashCode();
        result = 31 * result + guests;
        return result;
    }
}
