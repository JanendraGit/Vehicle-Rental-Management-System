package org.example.vehiclerentalmanagementsystem.service;

import org.example.vehiclerentalmanagementsystem.dto.Request.UserRequest;
import org.example.vehiclerentalmanagementsystem.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserRequest userRequest);
    void updateUser(Long userId, UserRequest userRequest);
    void deleteUser(Long userId);
    UserDTO getUserById(Long userId);
    List<UserDTO> getAllUsers();
}
