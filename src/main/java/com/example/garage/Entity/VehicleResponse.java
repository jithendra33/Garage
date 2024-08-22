package com.example.garage.Entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponse {
	private Long id;
	private String vehicleName;
	private String vehicleModel;
	private String vehicleNumber;
	private String price;
	private List<String> images;
}


