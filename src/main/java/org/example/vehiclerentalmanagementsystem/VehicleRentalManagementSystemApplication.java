package org.example.vehiclerentalmanagementsystem;

import org.example.vehiclerentalmanagementsystem.dto.Request.UserRequest;
import org.example.vehiclerentalmanagementsystem.dto.Request.VehicleRequest;
import org.example.vehiclerentalmanagementsystem.entity.Role;
import org.example.vehiclerentalmanagementsystem.entity.User;
import org.example.vehiclerentalmanagementsystem.mapper.UserMapper;
import org.example.vehiclerentalmanagementsystem.repository.UserRepository;
import org.example.vehiclerentalmanagementsystem.service.UserService;
import org.example.vehiclerentalmanagementsystem.service.VehicleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class VehicleRentalManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehicleRentalManagementSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner demoData(UserService userService, VehicleService vehicleService, UserRepository userRepository) {
        return args -> {
            try {
                // Create Sample Users
                if (userRepository.findByEmail("john@example.com").isEmpty()) {
                    UserRequest user1 = new UserRequest();
                    user1.setUserName("John Doe");
                    user1.setEmail("john@example.com");
                    user1.setPassword("password123");
                    // Manually saving to set role as ADMIN for demo purposes
                    User adminUser = UserMapper.toEntity(user1);
                    adminUser.setRole(Role.ADMIN);
                    userRepository.save(adminUser);
                }

                if (userRepository.findByEmail("alice@example.com").isEmpty()) {
                    UserRequest user2 = new UserRequest();
                    user2.setUserName("Alice Smith");
                    user2.setEmail("alice@example.com");
                    user2.setPassword("securepass");
                    userService.createUser(user2); // This will be USER by default
                }

                // Create Sample Vehicles
                if (vehicleService.getAllVehicles().isEmpty()) {
                    VehicleRequest v1 = new VehicleRequest();
                    v1.setVehicleNumber("BMW-X5-001");
                    v1.setVehicleType("SUV");
                    v1.setNumberOfSeats(5);
                    v1.setPricePerDay(150.0);
                    v1.setPricePerKilometer(2.5);
                    v1.setImage("https://images.unsplash.com/photo-1556189250-72ba954522af?auto=format&fit=crop&w=800&q=80");
                    vehicleService.createVehicle(v1);

                    VehicleRequest v2 = new VehicleRequest();
                    v2.setVehicleNumber("TESLA-S-002");
                    v2.setVehicleType("Luxury");
                    v2.setNumberOfSeats(5);
                    v2.setPricePerDay(200.0);
                    v2.setPricePerKilometer(1.5);
                    v2.setImage("https://images.unsplash.com/photo-1617788138017-80ad40651399?auto=format&fit=crop&w=800&q=80");
                    vehicleService.createVehicle(v2);

                    VehicleRequest v3 = new VehicleRequest();
                    v3.setVehicleNumber("AUDI-A4-003");
                    v3.setVehicleType("Sedan");
                    v3.setNumberOfSeats(5);
                    v3.setPricePerDay(120.0);
                    v3.setPricePerKilometer(2.0);
                    v3.setImage("https://images.unsplash.com/photo-1603584173870-7f23fdae1b7a?auto=format&fit=crop&w=800&q=80");
                    vehicleService.createVehicle(v3);

                    VehicleRequest v4 = new VehicleRequest();
                    v4.setVehicleNumber("MUSTANG-GT-004");
                    v4.setVehicleType("Sports");
                    v4.setNumberOfSeats(4);
                    v4.setPricePerDay(250.0);
                    v4.setPricePerKilometer(3.0);
                    v4.setImage("https://images.unsplash.com/photo-1492144534655-ae79c964c9d7?auto=format&fit=crop&w=800&q=80");
                    vehicleService.createVehicle(v4);
                }

                System.out.println("Sample data initialized successfully!");
            } catch (Exception e) {
                System.out.println("Sample data initialization skipped or error: " + e.getMessage());
            }
        };
    }
}
