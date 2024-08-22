package com.example.garage.UserVehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/user/vehicles")
public class UserVehicleController {

    @Autowired
    private PendingVehicleService pendingVehicleService;

    @PostMapping("/submit")
    public ResponseEntity<String> submitVehicle(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("vehicleName") String vehicleName,
            @RequestParam("vehicleNumber") String vehicleNumber,
            @RequestParam("vehicleModel") String vehicleModel,
            @RequestParam("price") String price) throws IOException, SerialException, SQLException {

        pendingVehicleService.savePendingVehicle(files, vehicleName, vehicleNumber, vehicleModel, price);
        return ResponseEntity.status(HttpStatus.CREATED).body("Vehicle submission successful. Waiting for admin approval.");
    }
}
