package org.example.vehiclerentalmanagementsystem.controller;

import lombok.Data;
import org.example.vehiclerentalmanagementsystem.dto.AdminDTO;
import org.example.vehiclerentalmanagementsystem.dto.Request.AdminRequest;
import org.example.vehiclerentalmanagementsystem.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/admins")
public class AdminController {
    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<AdminDTO> createAdmin(@RequestBody AdminRequest adminRequest) {
        AdminDTO createdAdmin = adminService.createAdmin(adminRequest);
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AdminDTO> updateAdmin(@PathVariable Long id, @RequestBody AdminRequest adminRequest) {
        AdminDTO updatedAdmin = adminService.updateAdmin(id,adminRequest);
        return new ResponseEntity<>(updatedAdmin, HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable Long id) {
        AdminDTO adminDTO = adminService.getAdminById(id);
        return new ResponseEntity<>(adminDTO, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        List<AdminDTO> admins = adminService.getAllAdmins();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }
    
}
