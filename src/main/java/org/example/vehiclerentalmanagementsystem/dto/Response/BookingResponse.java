package org.example.vehiclerentalmanagementsystem.dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingResponse {
    private Long id;
    private String userName;
    private String vehicleNumber;
    private String startDate;
    private String endDate;
    private int totalDays;
    private double totalPrice;
    private double totalKilometer;
    private String status;

}
