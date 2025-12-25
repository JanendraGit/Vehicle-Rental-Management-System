package org.example.vehiclerentalmanagementsystem.dto.Request;

import lombok.Data;

@Data
public class VehicleRequest {
    private String vehicleNumber;
    private String vehicleType;
    private int numberOfSeats;
    private double pricePerDay;
    private double pricePerKilometer;
    private String image;
}
