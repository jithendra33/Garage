package com.example.garage.UserVehicle;

import com.example.garage.Entity.VehicleEntity;
import com.example.garage.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/vehicles")
public class AdminVehicleController {

    @Autowired
    private PendingVehicleRepository pendingVehicleRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private PendingVehicleService pendingVehicleService;

    @PostMapping("/approve/{id}")
    public ResponseEntity<String> approveVehicle(@PathVariable Long id) throws IOException, SerialException, SQLException {
        PendingVehicleEntity pendingVehicle = pendingVehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid vehicle ID"));

        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setVehicleName(pendingVehicle.getVehicleName());
        vehicleEntity.setVehicleNumber(pendingVehicle.getVehicleNumber());
        vehicleEntity.setVehicleModel(pendingVehicle.getVehicleModel());
        vehicleEntity.setPrice(pendingVehicle.getPrice());

        List<Blob> imagesCopy = new ArrayList<>();
        for (Blob blob : pendingVehicle.getImages()) {
            byte[] blobBytes = blob.getBytes(1, (int) blob.length());
            Blob blobCopy = new SerialBlob(blobBytes);
            imagesCopy.add(blobCopy);
        }
        vehicleEntity.setImages(imagesCopy);

        vehicleRepository.save(vehicleEntity);
        pendingVehicleRepository.deleteById(id);

        return ResponseEntity.ok("Vehicle approved and moved to main list.");
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<String> rejectVehicle(@PathVariable Long id) {
        pendingVehicleRepository.deleteById(id);
        return ResponseEntity.ok("Vehicle rejected.");
    }

    @GetMapping("/pending")
    public ResponseEntity<List<PendingVehicleResponse>> getPendingVehicles() throws SQLException, IOException {
        List<PendingVehicleResponse> pendingVehicles = pendingVehicleService.getPendingVehicles();
        return ResponseEntity.ok(pendingVehicles);
    }
    @GetMapping(value = "/image/{id}/{index}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable Long id, @PathVariable int index) throws SQLException, IOException {
        PendingVehicleEntity pendingVehicle = pendingVehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid vehicle ID"));
        Blob imageBlob = pendingVehicle.getImages().get(index);
        byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
        return ResponseEntity.ok(imageBytes);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalVehicleCount() {
        long vehicleCount = vehicleRepository.count();
        return ResponseEntity.ok(vehicleCount);
    }

    }