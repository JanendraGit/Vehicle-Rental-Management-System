package org.example.vehiclerentalmanagementsystem.repository;

import org.example.vehiclerentalmanagementsystem.entity.Booking;
import org.example.vehiclerentalmanagementsystem.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByVehicleIdAndStatusAndEndDateAfterAndStartDateBefore(Long id, BookingStatus bookingStatus, LocalDate startDate, LocalDate endDate);
}
