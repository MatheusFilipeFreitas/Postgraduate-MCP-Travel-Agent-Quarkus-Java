package com.mathffreitas.booking.repository.implementation;

import com.mathffreitas.booking.models.Booking;
import com.mathffreitas.booking.models.types.BookingStatus;
import com.mathffreitas.booking.repository.BookingRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class BookingRepositoryImpl implements BookingRepository {
    private final Map<Long, Booking> bookings = new HashMap<>();

    @PostConstruct
    void initMockData() {
        Booking mock1 = new Booking(
                12345L,
                "John Doe",
                "Egyptian Treasures",
                LocalDate.now().plusMonths(2),
                LocalDate.now().plusMonths(2),
                BookingStatus.CONFIRMED
        );
        bookings.put(mock1.id(), mock1);

        Booking mock2 = new Booking(
                67890L,
                "Jane Smith",
                "Amazon Adventure",
                LocalDate.now().plusMonths(3),
                LocalDate.now().plusMonths(4),
                BookingStatus.PENDING
        );
        bookings.put(mock2.id(), mock2);
    }

    @Override
    public Optional<Booking> findById(long id) {
        return Optional.ofNullable(bookings.get(id));
    }

    @Override
    public Optional<Booking> cancelBooking(long id, String name) {
        Booking booking = findById(id).orElse(null);

        if (booking != null && booking.customerName().equals(name)) {
            Booking cancelledBooking = updateBookingStatus(booking, BookingStatus.CANCELLED);
            bookings.put(id, cancelledBooking);
            return Optional.of(cancelledBooking);
        }

        return Optional.empty();
    }

    private Booking updateBookingStatus(Booking booking, BookingStatus newStatus) {
        return new Booking(
                booking.id(),
                booking.customerName(),
                booking.destination(),
                booking.startDate(),
                booking.endDate(),
                newStatus
        );
    }
}
