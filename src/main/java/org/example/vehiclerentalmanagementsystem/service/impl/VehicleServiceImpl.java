package org.example.vehiclerentalmanagementsystem.service.impl;

import lombok.Data;
import org.example.vehiclerentalmanagementsystem.dto.Request.VehicleRequest;
import org.example.vehiclerentalmanagementsystem.dto.VehicleDTO;
import org.example.vehiclerentalmanagementsystem.entity.Vehicle;
import org.example.vehiclerentalmanagementsystem.exception.VehicleNotFoundException;
import org.example.vehiclerentalmanagementsystem.mapper.VehicleMapper;
import org.example.vehiclerentalmanagementsystem.repository.VehicleRepository;
import org.example.vehiclerentalmanagementsystem.service.VehicleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    @Override
    public VehicleDTO createVehicle(VehicleRequest vehicleRequest) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findByVehicleNumber(vehicleRequest.getVehicleNumber());
        if (optionalVehicle.isPresent()){
            throw new RuntimeException("Vehicle already exists");
        }
        Vehicle vehicle = VehicleMapper.toEntity(vehicleRequest);
        Vehicle saveVehicle = vehicleRepository.save(vehicle);
        return VehicleMapper.toDTO(saveVehicle);
    }

    @Override
    public void updateVehicle(Long vehicleId, VehicleRequest vehicleRequest) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));
        vehicle.setVehicleNumber(vehicleRequest.getVehicleNumber());
        vehicle.setVehicleType(vehicleRequest.getVehicleType());
        vehicle.setNumberOfSeats(vehicleRequest.getNumberOfSeats());
        vehicle.setPricePerDay(vehicleRequest.getPricePerDay());
        vehicle.setPricePerKilometer(vehicleRequest.getPricePerKilometer());
        vehicle.setImage(vehicleRequest.getImage());
        vehicleRepository.save(vehicle);
    }

    @Override
    public void deleteVehicle(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));
        vehicleRepository.delete(vehicle);
    }

    @Override
    public VehicleDTO getVehicleById(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));
        return VehicleMapper.toDTO(vehicle);
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles.stream()
                .map(VehicleMapper::toDTO)
                .collect(Collectors.toList());
    }
}
