package org.example.vehiclerentalmanagementsystem.controller;

import lombok.Data;
import org.example.vehiclerentalmanagementsystem.dto.BookingDTO;
import org.example.vehiclerentalmanagementsystem.dto.Request.BookingRequest;
import org.example.vehiclerentalmanagementsystem.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingRequest bookingRequest) {
        BookingDTO createdBooking = bookingService.createBooking(bookingRequest, bookingRequest.getUserEmail());
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBooking(@PathVariable Long id, @RequestBody BookingRequest bookingRequest) {
        bookingService.updateBooking(id, bookingRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        BookingDTO bookingDTO = bookingService.getBookingById(id);
        return new ResponseEntity<>(bookingDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.getAllBooking();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
}
