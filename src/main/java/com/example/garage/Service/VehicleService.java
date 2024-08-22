package com.example.garage.Service;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.garage.Entity.VehicleEntity;
import com.example.garage.Entity.VehicleResponse;
import com.example.garage.Repository.VehicleRepository;
import com.example.garage.UserController.UserIsNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Transactional
    public VehicleEntity saveVehicle(HttpServletRequest request, @Valid MultipartFile[] files, String vehicleName,
            String vehicleNumber, String vehicleModel, String price)
            throws IOException, SerialException, SQLException {

        // Check if vehicle number already exists
        Optional<VehicleEntity> existingVehicle = vehicleRepository.findByVehicleNumber(vehicleNumber);
        if (existingVehicle.isPresent()) {
            throw new IllegalArgumentException("Vehicle with number " + vehicleNumber + " already exists.");
        }

        List<Blob> blobs = new ArrayList<>();
        for (MultipartFile file : files) {
            byte[] bytes = file.getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
            blobs.add(blob);
        }

        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setImages(blobs);
        vehicleEntity.setVehicleModel(vehicleModel);
        vehicleEntity.setVehicleName(vehicleName);
        vehicleEntity.setVehicleNumber(vehicleNumber);
        vehicleEntity.setPrice(price);

        return vehicleRepository.save(vehicleEntity);
    }    
//////////////////////////////////    ALL VEHICLES   //////////////////////////////////////////////////
    
    @Transactional
    public List<VehicleResponse> getAllVehicles() {
        List<VehicleEntity> babyItems = vehicleRepository.findAll();
        List<VehicleResponse> vehicleResponses = new ArrayList<>();

        babyItems.forEach(e -> {
            VehicleResponse vehicleResponse = new VehicleResponse();
            vehicleResponse.setId(e.getId());
            vehicleResponse.setPrice(e.getPrice());
            vehicleResponse.setVehicleModel(e.getVehicleModel());
            vehicleResponse.setVehicleName(e.getVehicleName());
            vehicleResponse.setVehicleNumber(e.getVehicleNumber());

            List<String> customImageUrls = new ArrayList<>();
            for (int i = 0; i < e.getImages().size(); i++) {
                String customImageUrl = "/vehicles/displayImage?id=" + e.getId() + "&imageIndex=" + i; // Custom URL
                customImageUrls.add(customImageUrl);
            }
            vehicleResponse.setImages(customImageUrls);

            vehicleResponses.add(vehicleResponse);
        });

        return vehicleResponses;
    }
    ///////////////////////////////////  GET BY ID   ////////////////////////////
    @Transactional
    public VehicleResponse getVehicleById(long id) {
        Optional<VehicleEntity> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isEmpty()) {
            throw new UserIsNotFoundException("Vehicle with ID " + id + " not found");
        }

        VehicleEntity vehicle = optionalVehicle.get();
        VehicleResponse vehicleResponse = createVehicleResponse(vehicle);
        return vehicleResponse;
    }

    private VehicleResponse createVehicleResponse(VehicleEntity vehicle) {
        VehicleResponse vehicleResponse = new VehicleResponse();
        vehicleResponse.setId(vehicle.getId());
        vehicleResponse.setVehicleName(vehicle.getVehicleName());
        vehicleResponse.setVehicleModel(vehicle.getVehicleModel());
        vehicleResponse.setVehicleNumber(vehicle.getVehicleNumber());
        vehicleResponse.setPrice(vehicle.getPrice());

        List<String> imageUrls = new ArrayList<>();
        for (int i = 0; i < vehicle.getImages().size(); i++) {
            String customImageUrl = "/Garage/displayImage?id=" + vehicle.getId() + "&imageIndex=" + i; // Custom URL
            imageUrls.add(customImageUrl);
        }
        vehicleResponse.setImages(imageUrls);

        return vehicleResponse;
    }
    
    ///////////////////   IMAGE  ///////////////////////////////
    public VehicleEntity getImageViewById(Long id) {
        Optional<VehicleEntity> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isEmpty()) {
            throw new UserIsNotFoundException("Image is not uploaded...");
        } else {
            return optionalVehicle.get();
        }
    }
    
    /////////////////////   SEARCH NAME   ////////////////////////////

    public List<VehicleResponse> getVehiclesByName(String vehicleName) {
        List<VehicleEntity> vehicles = vehicleRepository.findByVehicleName(vehicleName);
        List<VehicleResponse> vehicleResponses = new ArrayList<>();

        for (VehicleEntity vehicle : vehicles) {
            VehicleResponse vehicleResponse = createVehicleResponse(vehicle);
            vehicleResponses.add(vehicleResponse);
        }

        return vehicleResponses;
    }

    private VehicleResponse createVehicleResponses(VehicleEntity vehicle) {
        VehicleResponse vehicleResponse = new VehicleResponse();
        vehicleResponse.setId(vehicle.getId());
        vehicleResponse.setVehicleName(vehicle.getVehicleName());
        vehicleResponse.setVehicleModel(vehicle.getVehicleModel());
        vehicleResponse.setVehicleNumber(vehicle.getVehicleNumber());
        vehicleResponse.setPrice(vehicle.getPrice());

        List<String> imageUrls = new ArrayList<>();
        for (int i = 0; i < vehicle.getImages().size(); i++) {
            String customImageUrl = "/vehicles/displayImage?id=" + vehicle.getId() + "&imageIndex=" + i;
            imageUrls.add(customImageUrl);
        }
        vehicleResponse.setImages(imageUrls);

        return vehicleResponse;
    }
    
    
    //////////////////////////  VEHICLE MODEL  ///////////////////
    
    public List<VehicleResponse> getVehiclesByModel(String vehicleModel) {
        List<VehicleEntity> vehicles = vehicleRepository.findByVehicleModel(vehicleModel);
        List<VehicleResponse> vehicleResponses = new ArrayList<>();

        for (VehicleEntity vehicle : vehicles) {
            VehicleResponse vehicleResponse = createVehicleResponse(vehicle);
            vehicleResponses.add(vehicleResponse);
        }

        return vehicleResponses;
    }

    private VehicleResponse createVehicleResponsess(VehicleEntity vehicle) {
        VehicleResponse vehicleResponse = new VehicleResponse();
        vehicleResponse.setId(vehicle.getId());
        vehicleResponse.setVehicleName(vehicle.getVehicleName());
        vehicleResponse.setVehicleModel(vehicle.getVehicleModel());
        vehicleResponse.setVehicleNumber(vehicle.getVehicleNumber());
        vehicleResponse.setPrice(vehicle.getPrice());

        List<String> imageUrls = new ArrayList<>();
        for (int i = 0; i < vehicle.getImages().size(); i++) {
            String customImageUrl = "/vehicles/displayImage?id=" + vehicle.getId() + "&imageIndex=" + i;
            imageUrls.add(customImageUrl);
        }
        vehicleResponse.setImages(imageUrls);

        return vehicleResponse;
    }
    
    public String updateVehicle(long id, String vehicleName, String vehicleNumber, String vehicleModel, String price, MultipartFile[] files) throws IOException, SQLException {
        Optional<VehicleEntity> optionalExistingVehicle = vehicleRepository.findById(id);

        if (optionalExistingVehicle.isEmpty()) {
            throw new UserIsNotFoundException("Vehicle with ID " + id + " not found");
        }

        VehicleEntity existingVehicle = optionalExistingVehicle.get();
        existingVehicle.setVehicleName(vehicleName);
        existingVehicle.setVehicleNumber(vehicleNumber);
        existingVehicle.setVehicleModel(vehicleModel);
        existingVehicle.setPrice(price);

        if (files != null && files.length > 0) {
            existingVehicle.getImages().clear(); // Clear existing images
            for (MultipartFile file : files) {
                byte[] bytes = file.getBytes();
                Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
                existingVehicle.getImages().add(blob); // Add the new image to the list
            }
        }

        vehicleRepository.save(existingVehicle);
        return "Vehicle with ID " + id + " updated successfully.";
    }

    @Transactional
    public void deleteVehicle(long id) {
        Optional<VehicleEntity> optionalVehicle = vehicleRepository.findById(id);

        if (optionalVehicle.isPresent()) {
            vehicleRepository.deleteById(id);
        } else {
            throw new UserIsNotFoundException("Vehicle not found with this id " + id);
        }
    }
    
    
    @Transactional
    public void deleteVehicleByNumber(String vehicleNumber) {
        Optional<VehicleEntity> optionalVehicle = vehicleRepository.findByVehicleNumber(vehicleNumber);

        if (optionalVehicle.isPresent()) {
            vehicleRepository.deleteById(optionalVehicle.get().getId());
        } else {
            throw new UserIsNotFoundException("Vehicle not found with vehicle number " + vehicleNumber);
        }
    }
}




