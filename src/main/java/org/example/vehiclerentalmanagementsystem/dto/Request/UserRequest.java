package org.example.vehiclerentalmanagementsystem.dto.Request;

import lombok.Data;

@Data
public class UserRequest {
    private String userName;
    private String password;
    private String email;
}
