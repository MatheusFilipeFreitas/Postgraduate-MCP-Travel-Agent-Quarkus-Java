package com.mathffreitas.booking.service.implementation;

import com.mathffreitas.booking.models.Booking;
import com.mathffreitas.booking.repository.BookingRepository;
import com.mathffreitas.booking.service.BookingService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class BookingServiceImpl implements BookingService {

    @Inject
    BookingRepository bookingRepository;

    @Override
    public Optional<Booking> getBookingDetails(
            long id
    ) {
        return bookingRepository.findById(id);
    }

    @Override
    public Optional<Booking> cancelBooking(
            long id,
            String name
    ) {
        return bookingRepository.cancelBooking(id, name);
    }
}
