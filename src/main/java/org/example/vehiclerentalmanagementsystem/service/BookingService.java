package org.example.vehiclerentalmanagementsystem.service;

import org.example.vehiclerentalmanagementsystem.dto.BookingDTO;
import org.example.vehiclerentalmanagementsystem.dto.Request.BookingRequest;
import org.example.vehiclerentalmanagementsystem.dto.VehicleDTO;

import java.util.List;

public interface BookingService {
    BookingDTO createBooking(BookingRequest bookingRequest,String userEmail);
    void updateBooking(Long bookingId,BookingRequest bookingRequest);
    void deleteBooking(Long bookingId);
    BookingDTO getBookingById(Long bookingId);
    List<BookingDTO> getAllBooking();
}
