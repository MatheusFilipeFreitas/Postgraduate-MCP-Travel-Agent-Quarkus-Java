package com.mathffreitas.booking.repository;

import com.mathffreitas.booking.models.Booking;

import java.util.Optional;

public interface BookingRepository {
    Optional<Booking> findById(long id);
    Optional<Booking> cancelBooking(long id, String customerLastName);
}
