package org.example.vehiclerentalmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {
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
