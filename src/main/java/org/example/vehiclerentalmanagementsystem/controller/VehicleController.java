package org.example.vehiclerentalmanagementsystem.controller;

import lombok.Data;
import org.example.vehiclerentalmanagementsystem.dto.Request.VehicleRequest;
import org.example.vehiclerentalmanagementsystem.dto.VehicleDTO;
import org.example.vehiclerentalmanagementsystem.repository.UserRepository;
import org.example.vehiclerentalmanagementsystem.repository.VehicleRepository;
import org.example.vehiclerentalmanagementsystem.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleDTO> createVehicle(@RequestBody VehicleRequest vehicleRequest) {
        VehicleDTO createdVehicle = vehicleService.createVehicle(vehicleRequest);
        return new ResponseEntity<>(createdVehicle, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateVehicle(@PathVariable Long id, @RequestBody VehicleRequest vehicleRequest) {
        vehicleService.updateVehicle(id, vehicleRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable Long id) {
        VehicleDTO vehicleDTO = vehicleService.getVehicleById(id);
        return new ResponseEntity<>(vehicleDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
        List<VehicleDTO> vehicles = vehicleService.getAllVehicles();
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }
}
