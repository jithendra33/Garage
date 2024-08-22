package com.example.garage.UserVehicle;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendingVehicleResponse {
    private Long id;
    private String vehicleName;
    private String vehicleNumber;
    private String vehicleModel;
    private String price;
    private List<String> images; // Base64 encoded images

    // Getters and setters
}
