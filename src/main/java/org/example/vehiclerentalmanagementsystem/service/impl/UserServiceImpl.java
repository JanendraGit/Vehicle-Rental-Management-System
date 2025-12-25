package org.example.vehiclerentalmanagementsystem.service.impl;

import lombok.Data;
import org.example.vehiclerentalmanagementsystem.dto.Request.UserRequest;
import org.example.vehiclerentalmanagementsystem.dto.UserDTO;
import org.example.vehiclerentalmanagementsystem.entity.User;
import org.example.vehiclerentalmanagementsystem.exception.UserNotFoundException;
import org.example.vehiclerentalmanagementsystem.mapper.UserMapper;
import org.example.vehiclerentalmanagementsystem.repository.UserRepository;
import org.example.vehiclerentalmanagementsystem.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDTO createUser(UserRequest userRequest) {
        Optional<User> user1 = userRepository.findByEmail(userRequest.getEmail());
        if (user1.isPresent()) {
            throw new RuntimeException("User already exists");
        }
        User user = UserMapper.toEntity(userRequest);
        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    @Override
    public void updateUser(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found this id: "+userId));
        user.setUsername(userRequest.getUserName());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }
}
