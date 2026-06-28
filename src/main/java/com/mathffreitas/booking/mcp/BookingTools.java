package com.mathffreitas.booking.mcp;

import com.mathffreitas.booking.models.Booking;
import com.mathffreitas.booking.service.BookingService;
import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import jakarta.inject.Inject;

public class BookingTools {

    @Inject
    BookingService bookingService;

    @Tool(description = """
            Looks up a travel booking by its numeric identifier.
            Use when the customer asks about reservation details, trip status, destination, or travel dates.
            Returns booking id, customer name, destination, start date, end date, and status (CONFIRMED, PENDING, or CANCELLED).
            If no booking exists for the given id, returns a not-found message.
            """)
    public String getBookingDetails(
            @ToolArg(description = "Numeric booking identifier provided by the customer, e.g. 12345")
            long bookingId
    ) {
        return bookingService.getBookingDetails(bookingId)
                .map(Booking::toString)
                .orElse("Cound not find booking with id " + bookingId);
    }

    @Tool(description = """
            Cancels an existing travel booking after identity verification.
            Use only when the customer explicitly requests cancellation and provides both their booking id and last name.
            The last name must match the end of the name on the booking (e.g. 'Doe' for 'John Doe').
            Returns the updated booking with CANCELLED status on success.
            Returns a not-found message if the booking does not exist or the last name does not match.
            """)
    public String cancelBooking(
            @ToolArg(description = "Numeric booking identifier to cancel")
            long bookingId,
            @ToolArg(description = "Customer last name as registered on the booking, used to verify identity before cancellation")
            String customerLastName
    ) {
        return bookingService.cancelBooking(bookingId, customerLastName)
                .map(Booking::toString)
                .orElse("Cound not find booking with id " + bookingId);
    }
}
