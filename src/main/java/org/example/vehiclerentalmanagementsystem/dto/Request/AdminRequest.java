package org.example.vehiclerentalmanagementsystem.dto.Request;

import lombok.Data;

@Data
public class AdminRequest {
    private String username;
    private String password;
    private String email;
}
