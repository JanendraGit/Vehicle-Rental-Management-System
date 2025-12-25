package org.example.vehiclerentalmanagementsystem.dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehicleResponse {
    private Long id;
    private String vehicleNumber;
    private String vehicleType;
    private int numberOfSeats;
    private double pricePerDay;
    private double pricePerKilometer;
    private String image;
    private boolean active;
    private boolean available;

}
