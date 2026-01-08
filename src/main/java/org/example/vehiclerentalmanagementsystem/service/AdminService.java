package org.example.vehiclerentalmanagementsystem.service;

import org.example.vehiclerentalmanagementsystem.dto.AdminDTO;
import org.example.vehiclerentalmanagementsystem.dto.Request.AdminRequest;

import java.util.List;

public interface AdminService {
    AdminDTO createAdmin(AdminRequest adminRequest);
    AdminDTO updateAdmin(Long id, AdminRequest adminRequest);
    void deleteAdmin(Long id);
    AdminDTO getAdminById(Long id);
    List<AdminDTO> getAllAdmins();
}
