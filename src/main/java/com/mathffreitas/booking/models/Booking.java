package com.mathffreitas.booking.models;

import com.mathffreitas.booking.models.types.BookingStatus;

import java.time.LocalDate;

public record Booking(
        Long id,
        String customerName,
        String destination,
        LocalDate startDate,
        LocalDate endDate,
        BookingStatus status
) {
}
