package org.example.vehiclerentalmanagementsystem.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class AdminResponse {
    private Long id;
    private String username;
    private String email;
}
