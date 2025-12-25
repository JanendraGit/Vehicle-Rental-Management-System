package org.example.vehiclerentalmanagementsystem.service.impl;

import lombok.Data;
import org.example.vehiclerentalmanagementsystem.dto.BookingDTO;
import org.example.vehiclerentalmanagementsystem.dto.Request.BookingRequest;
import org.example.vehiclerentalmanagementsystem.entity.Booking;
import org.example.vehiclerentalmanagementsystem.entity.BookingStatus;
import org.example.vehiclerentalmanagementsystem.entity.User;
import org.example.vehiclerentalmanagementsystem.entity.Vehicle;
import org.example.vehiclerentalmanagementsystem.exception.BookingNotFoundException;
import org.example.vehiclerentalmanagementsystem.exception.UserNotFoundException;
import org.example.vehiclerentalmanagementsystem.exception.VehicleNotFoundException;
import org.example.vehiclerentalmanagementsystem.mapper.BookingMapper;
import org.example.vehiclerentalmanagementsystem.repository.BookingRepository;
import org.example.vehiclerentalmanagementsystem.repository.UserRepository;
import org.example.vehiclerentalmanagementsystem.repository.VehicleRepository;
import org.example.vehiclerentalmanagementsystem.service.BookingService;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class BookingImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public BookingDTO createBooking(BookingRequest bookingRequest,String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(()->new UserNotFoundException("user not found"));
        Vehicle vehicle = vehicleRepository.findById(bookingRequest.getVehicleId())
                .orElseThrow(()->new VehicleNotFoundException("vehicle not found"));
        List<Booking> existingBookings = bookingRepository.findByVehicleIdAndStatusAndEndDateAfterAndStartDateBefore(
                vehicle.getId(),
                BookingStatus.APPROVED,
                bookingRequest.getStartDate(),
                bookingRequest.getEndDate()
        );
        if (!existingBookings.isEmpty()){
            throw new RuntimeException("vehicle is not available");
        }
        int days = (int) ChronoUnit.DAYS.between(bookingRequest.getStartDate(), bookingRequest.getEndDate());
        double totalPrice = (days * vehicle.getPricePerDay())+(vehicle.getPricePerKilometer()*bookingRequest.getTotalKilometer());

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setVehicle(vehicle);
        booking.setStartDate(bookingRequest.getStartDate());
        booking.setEndDate(bookingRequest.getEndDate());
        booking.setTotalPrice(totalPrice);
        booking.setTotalDays(days);
        booking.setStatus(BookingStatus.PENDING);
        booking.setTotalKilometer(bookingRequest.getTotalKilometer());
        Booking saveBooking = bookingRepository.save(booking);
        return BookingMapper.toDTO(saveBooking);
    }

    @Override
    public void updateBooking(Long bookingId, BookingRequest bookingRequest) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(()->new BookingNotFoundException("booking not found"));
        booking.setStartDate(bookingRequest.getStartDate());
        booking.setEndDate(bookingRequest.getEndDate());
        bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(()->new BookingNotFoundException("booking not found"));
        bookingRepository.delete(booking);
    }

    @Override
    public BookingDTO getBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(()->new BookingNotFoundException("booking not found"));
        return BookingMapper.toDTO(booking);
    }

    @Override
    public List<BookingDTO> getAllBooking() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(BookingMapper::toDTO)
                .collect(Collectors.toList());
    }
}
