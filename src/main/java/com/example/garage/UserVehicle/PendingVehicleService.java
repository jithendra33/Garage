package com.example.garage.UserVehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Service
public class PendingVehicleService {
    @Autowired
    private PendingVehicleRepository pendingVehicleRepository;

    public PendingVehicleEntity savePendingVehicle(MultipartFile[] files, String vehicleName, String vehicleNumber, String vehicleModel, String price) throws IOException, SQLException {
        List<Blob> blobs = new ArrayList<>();
        for (MultipartFile file : files) {
            byte[] bytes = file.getBytes();
            Blob blob = new SerialBlob(bytes);
            blobs.add(blob);
        }

        PendingVehicleEntity pendingVehicleEntity = new PendingVehicleEntity();
        pendingVehicleEntity.setVehicleName(vehicleName);
        pendingVehicleEntity.setVehicleNumber(vehicleNumber);
        pendingVehicleEntity.setVehicleModel(vehicleModel);
        pendingVehicleEntity.setPrice(price);
        pendingVehicleEntity.setImages(blobs);

        return pendingVehicleRepository.save(pendingVehicleEntity);
    }

    public List<PendingVehicleResponse> getPendingVehicles() throws SQLException, IOException {
        List<PendingVehicleEntity> pendingVehicles = pendingVehicleRepository.findAll();
        List<PendingVehicleResponse> responses = new ArrayList<>();

        for (PendingVehicleEntity pendingVehicle : pendingVehicles) {
            PendingVehicleResponse response = new PendingVehicleResponse();
            response.setId(pendingVehicle.getId());
            response.setVehicleName(pendingVehicle.getVehicleName());
            response.setVehicleNumber(pendingVehicle.getVehicleNumber());
            response.setVehicleModel(pendingVehicle.getVehicleModel());
            response.setPrice(pendingVehicle.getPrice());

            List<String> imageUrls = new ArrayList<>();
            for (int i = 0; i < pendingVehicle.getImages().size(); i++) {
                String imageUrl = "/admin/vehicles/image/" + pendingVehicle.getId() + "/" + i; // Short URL
                imageUrls.add(imageUrl);
            }
            response.setImages(imageUrls);
            responses.add(response);
        }
        return responses;
      }
}