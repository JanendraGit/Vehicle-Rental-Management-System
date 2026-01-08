package org.example.vehiclerentalmanagementsystem.mapper;

import org.example.vehiclerentalmanagementsystem.dto.AdminDTO;
import org.example.vehiclerentalmanagementsystem.dto.Request.AdminRequest;
import org.example.vehiclerentalmanagementsystem.entity.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {
    private AdminMapper(){

    }
    public static Admin toEntity(AdminRequest request){
        if(request == null){
            return null;
        }
        Admin admin = new Admin();
        admin.setUsername(request.getUsername());
        admin.setPassword(request.getPassword());
        admin.setEmail(request.getEmail());
        return admin;
    }

    public static AdminDTO toDTO(Admin admin){
        if(admin == null){
            return null;
        }
        AdminDTO dto = new AdminDTO();
        dto.setId(admin.getId());
        dto.setUsername(admin.getUsername());
        dto.setEmail(admin.getEmail());
        return dto;
    }

}
