package org.example.vehiclerentalmanagementsystem.repository;

import org.example.vehiclerentalmanagementsystem.dto.UserDTO;
import org.example.vehiclerentalmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
