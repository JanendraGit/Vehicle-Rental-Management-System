package org.example.vehiclerentalmanagementsystem.dto.Request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {
    private LocalDate startDate;
    private  LocalDate endDate;
    private int totalDays;
    private double totalKilometer;
    private Long vehicleId;
    private String userEmail;
}
