package com.mathffreitas.booking.service;

import com.mathffreitas.booking.models.Booking;

import java.util.Optional;

public interface BookingService {
    Optional<Booking> getBookingDetails(long id);
    Optional<Booking> cancelBooking(long id, String name);
}
