package com.example.garage.Controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.garage.Entity.VehicleEntity;
import com.example.garage.Entity.VehicleResponse;
import com.example.garage.Service.VehicleService;
import com.example.garage.UserEntity.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/Garage")
public class VehicleController {
	
	@Autowired
	public VehicleService vehicleService;

	@PostMapping("/save")
    public ResponseEntity<ResponseBody<VehicleResponse>> saveVehicles(
            @RequestParam("files") @Valid MultipartFile[] files,
            @RequestParam("vehicleName") String vehicleName,
            @RequestParam("vehicleNumber") String vehicleNumber,
            @RequestParam("vehicleModel") String vehicleModel,
            @RequestParam("price") String price,
            HttpServletRequest request) throws IOException, SerialException, SQLException {

        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("At least one image file is required");
        }

        ResponseBody<VehicleResponse> responseBody = new ResponseBody<>();

        try {
            VehicleEntity vehicleEntity = vehicleService.saveVehicle(request, files, vehicleName, vehicleNumber, vehicleModel, price);

            VehicleResponse vehicleResponse = new VehicleResponse();
            vehicleResponse.setId(vehicleEntity.getId());
            vehicleResponse.setVehicleModel(vehicleEntity.getVehicleModel());
            vehicleResponse.setVehicleName(vehicleEntity.getVehicleName());
            vehicleResponse.setVehicleNumber(vehicleEntity.getVehicleNumber());
            vehicleResponse.setPrice(vehicleEntity.getPrice());

            List<String> imageUrls = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                String customImageUrl = "/vehicles/displayImage?id=" + vehicleEntity.getId() + "&imageIndex=" + i; // Custom URL
                imageUrls.add(customImageUrl);
            }
            vehicleResponse.setImages(imageUrls);

            responseBody.setStatusCode(HttpStatus.OK.value());
            responseBody.setStatus("SUCCESS");
            responseBody.setData(vehicleResponse);

        } catch (IllegalArgumentException e) {
            responseBody.setStatusCode(HttpStatus.CONFLICT.value());
            responseBody.setStatus(e.getMessage());
        }

        return ResponseEntity.ok(responseBody);
    }

	@GetMapping("/allVehicles")
    public ResponseEntity<?> getAllVehicles() {
        List<VehicleResponse> list = vehicleService.getAllVehicles();

        ResponseBody<List<VehicleResponse>> vehiclesBody = new ResponseBody<>();
        vehiclesBody.setStatusCode(HttpStatus.OK.value());
        vehiclesBody.setStatus("SUCCESS");
        vehiclesBody.setData(list);

        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .body(vehiclesBody);
    }

	@GetMapping("/getById/{id}")
	public ResponseEntity<ResponseBody<VehicleResponse>> getVehicleById(@PathVariable("id") long id) {
	    VehicleResponse vehicleResponse = vehicleService.getVehicleById(id);
	    ResponseBody<VehicleResponse> responseBody = new ResponseBody<>();
	    responseBody.setStatusCode(HttpStatus.OK.value());
	    responseBody.setStatus("SUCCESS");
	    responseBody.setData(vehicleResponse);
	    return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body(responseBody);
	}


	
	 @GetMapping("/displayImage")
	    @Transactional(readOnly = true)
	    public ResponseEntity<byte[]> displayVehicleImage(@RequestParam("id") Long id, @RequestParam("imageIndex") int imageIndex) throws SQLException {
	        VehicleEntity vehicle = vehicleService.getImageViewById(id);
	        if (vehicle != null && imageIndex >= 0 && imageIndex < vehicle.getImages().size()) {
	            Blob imageBlob = vehicle.getImages().get(imageIndex);
	            byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
	            return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(imageBytes);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	
	    

	    @GetMapping("/search/{vehicleName}")
	    public ResponseEntity<ResponseBody<List<VehicleResponse>>> getVehiclesByName(@PathVariable String vehicleName) {
	        List<VehicleResponse> vehicleResponses = vehicleService.getVehiclesByName(vehicleName);
	        ResponseBody<List<VehicleResponse>> vehicleBody = new ResponseBody<>();

	        if (vehicleResponses.isEmpty()) {
	            vehicleBody.setStatusCode(HttpStatus.NO_CONTENT.value());
	            vehicleBody.setStatus("No vehicles available with this given name.");
	        } else {
	            vehicleBody.setStatusCode(HttpStatus.OK.value());
	            vehicleBody.setStatus("SUCCESS");
	            vehicleBody.setData(vehicleResponses);
	        }

	        return ResponseEntity.ok(vehicleBody);
	    }
	    
	    @GetMapping("/search/model/{vehicleModel}")
	    public ResponseEntity<ResponseBody<List<VehicleResponse>>> getVehiclesByModel(@PathVariable String vehicleModel) {
	        List<VehicleResponse> vehicleResponses = vehicleService.getVehiclesByModel(vehicleModel);
	        ResponseBody<List<VehicleResponse>> vehicleBody = new ResponseBody<>();

	        if (vehicleResponses.isEmpty()) {
	            vehicleBody.setStatusCode(HttpStatus.NO_CONTENT.value());
	            vehicleBody.setStatus("No vehicles available with this given model.");
	        } else {
	            vehicleBody.setStatusCode(HttpStatus.OK.value());
	            vehicleBody.setStatus("SUCCESS");
	            vehicleBody.setData(vehicleResponses);
	        }

	        return ResponseEntity.ok(vehicleBody);
	    }
	


	    @PutMapping("/updateBy/{id}")
	    public ResponseEntity<ResponseBody<String>> updateVehicle(
	            @PathVariable("id") long id,
	            @RequestParam("files") MultipartFile[] files,
	            @RequestParam("vehicleName") String vehicleName,
	            @RequestParam("vehicleNumber") String vehicleNumber,
	            @RequestParam("vehicleModel") String vehicleModel,
	            @RequestParam("price") String price) throws IOException, SQLException {
	        
	        String message = vehicleService.updateVehicle(id, vehicleName, vehicleNumber, vehicleModel, price, files);
	        ResponseBody<String> responseBody = new ResponseBody<>();
	        responseBody.setStatusCode(HttpStatus.OK.value());
	        responseBody.setStatus("SUCCESS");
	        responseBody.setData(message);
	        return ResponseEntity.ok(responseBody);
	    }


    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<ResponseBody<String>> deleteVehicleById(@PathVariable long id) {
           vehicleService.deleteVehicle(id);
    ResponseBody<String> responseBody = new ResponseBody<>();
    responseBody.setStatusCode(HttpStatus.OK.value());
    responseBody.setStatus("SUCCESS");
    responseBody.setData("Vehicle with ID " + id + " deleted successfully.");
    return ResponseEntity.ok(responseBody);
  }
    
    @DeleteMapping("/deleteByNumberPlate/{vehicleNumber}")
    public ResponseEntity<ResponseBody<String>> deleteVehicleByNumber(@PathVariable String vehicleNumber) {
        vehicleService.deleteVehicleByNumber(vehicleNumber);
        ResponseBody<String> responseBody = new ResponseBody<>();
        responseBody.setStatusCode(HttpStatus.OK.value());
        responseBody.setStatus("SUCCESS");
        responseBody.setData("Vehicle with vehicle number " + vehicleNumber + " deleted successfully.");
        return ResponseEntity.ok(responseBody);
    }
}