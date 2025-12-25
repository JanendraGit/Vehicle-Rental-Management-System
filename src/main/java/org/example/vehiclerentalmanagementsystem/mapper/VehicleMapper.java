package org.example.vehiclerentalmanagementsystem.mapper;

import org.example.vehiclerentalmanagementsystem.dto.Request.VehicleRequest;
import org.example.vehiclerentalmanagementsystem.dto.VehicleDTO;
import org.example.vehiclerentalmanagementsystem.entity.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {
    private VehicleMapper(){

    }
    public static Vehicle toEntity(VehicleRequest request){
        if(request == null){
            return null;
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber(request.getVehicleNumber());
        vehicle.setVehicleType(request.getVehicleType());
        vehicle.setNumberOfSeats(request.getNumberOfSeats());
        vehicle.setPricePerDay(request.getPricePerDay());
        vehicle.setPricePerKilometer(request.getPricePerKilometer());
        vehicle.setImage(request.getImage());
        return vehicle;
    }

    public static VehicleDTO toDTO(Vehicle vehicle){
        if (vehicle == null){
            return null;
        }
        VehicleDTO dto = new VehicleDTO();
        dto.setId(vehicle.getId());
        dto.setVehicleNumber(vehicle.getVehicleNumber());
        dto.setVehicleType(vehicle.getVehicleType());
        dto.setNumberOfSeats(vehicle.getNumberOfSeats());
        dto.setPricePerDay(vehicle.getPricePerDay());
        dto.setPricePerKilometer(vehicle.getPricePerKilometer());
        dto.setImage(vehicle.getImage());
        dto.setActive(vehicle.isActive());
        dto.setAvailable(vehicle.isAvailable());
        return dto;
    }
}
