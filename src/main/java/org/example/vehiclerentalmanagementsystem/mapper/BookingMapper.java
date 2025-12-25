package org.example.vehiclerentalmanagementsystem.mapper;

import org.example.vehiclerentalmanagementsystem.dto.BookingDTO;
import org.example.vehiclerentalmanagementsystem.dto.Request.BookingRequest;
import org.example.vehiclerentalmanagementsystem.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    private BookingMapper(){

    }
    public static Booking toEntity(BookingRequest request){
        if(request == null){
            return null;
        }
        Booking booking = new Booking();
        booking.setStartDate(request.getStartDate());
        booking.setEndDate(request.getEndDate());
        booking.setTotalDays(request.getTotalDays());
        booking.setTotalKilometer(request.getTotalKilometer());
        return booking;
    }

    public static BookingDTO toDTO(Booking booking){
        if (booking == null){
            return null;
        }
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setUserName(booking.getUser().getUsername());
        dto.setVehicleName(booking.getVehicle().getVehicleNumber());
        dto.setStartDate(booking.getStartDate().toString());
        dto.setEndDate(booking.getEndDate().toString());
        dto.setTotalDays(booking.getTotalDays());
        dto.setTotalPrice(booking.getTotalPrice());
        dto.setTotalKilometer(booking.getTotalKilometer());
        dto.setStatus(booking.getStatus().toString());
        return dto;
    }
}
