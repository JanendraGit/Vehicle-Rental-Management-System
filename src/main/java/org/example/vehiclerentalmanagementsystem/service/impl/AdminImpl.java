package org.example.vehiclerentalmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.vehiclerentalmanagementsystem.dto.AdminDTO;
import org.example.vehiclerentalmanagementsystem.dto.Request.AdminRequest;
import org.example.vehiclerentalmanagementsystem.entity.Admin;
import org.example.vehiclerentalmanagementsystem.mapper.AdminMapper;
import org.example.vehiclerentalmanagementsystem.repository.AdminRepository;
import org.example.vehiclerentalmanagementsystem.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public AdminDTO createAdmin(AdminRequest adminRequest) {
        Optional<Admin> admin = adminRepository.findByEmail(adminRequest.getEmail());
        if (admin.isPresent()) {
            throw new RuntimeException("Admin already exists");
        }
        Admin admin1 = AdminMapper.toEntity(adminRequest);
        Admin savedAdmin = adminRepository.save(admin1);
        return AdminMapper.toDTO(savedAdmin);
    }

    @Override
    public AdminDTO updateAdmin(Long id, AdminRequest adminRequest) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        admin.setUsername(adminRequest.getUsername());
        admin.setPassword(adminRequest.getPassword());
        admin.setEmail(adminRequest.getEmail());
        Admin updatedAdmin = adminRepository.save(admin);
        return AdminMapper.toDTO(updatedAdmin);
    }

    @Override
    public void deleteAdmin(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        adminRepository.delete(admin);
    }

    @Override
    public AdminDTO getAdminById(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        return AdminMapper.toDTO(admin);
    }

    @Override
    public List<AdminDTO> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream()
                .map(AdminMapper::toDTO)
                .collect(Collectors.toList());
    }
}
