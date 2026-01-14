package org.example.vehiclerentalmanagementsystem.mapper;

import org.example.vehiclerentalmanagementsystem.dto.Request.UserRequest;
import org.example.vehiclerentalmanagementsystem.dto.UserDTO;
import org.example.vehiclerentalmanagementsystem.entity.User;

public class UserMapper {
    public UserMapper(){

    }
    public static User toEntity(UserRequest request){
        if(request == null){
            return null;
        }
        User user = new User();
        user.setUsername(request.getUserName());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        return user;
    }

    public static UserDTO toDTO(User user){
        if (user == null){
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUserName(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
