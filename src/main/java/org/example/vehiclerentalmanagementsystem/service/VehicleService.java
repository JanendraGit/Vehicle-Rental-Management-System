package org.example.vehiclerentalmanagementsystem.service;

import org.example.vehiclerentalmanagementsystem.dto.Request.VehicleRequest;
import org.example.vehiclerentalmanagementsystem.dto.VehicleDTO;

import java.util.List;

public interface VehicleService {
    VehicleDTO createVehicle(VehicleRequest vehicleRequest);
    void updateVehicle(Long vehicleId, VehicleRequest vehicleRequest);
    void deleteVehicle(Long vehicleId);
    VehicleDTO getVehicleById(Long vehicleId);
    List<VehicleDTO> getAllVehicles();
}
