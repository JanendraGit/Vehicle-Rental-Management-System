package org.example.vehiclerentalmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vehicleNumber;
    private String vehicleType;
    private int numberOfSeats;
    private double pricePerDay;
    private double pricePerKilometer;
    private String image;
    private boolean active = true;
    private boolean available = true;
    private LocalDate startDate;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<Booking> bookings;
}
