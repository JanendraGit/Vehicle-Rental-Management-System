package org.example.vehiclerentalmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private Long id;
    private String userName;
    private String vehicleName;
    private String startDate;
    private String endDate;
    private int totalDays;
    private double totalPrice;
    private double totalKilometer;
    private String status;
}
