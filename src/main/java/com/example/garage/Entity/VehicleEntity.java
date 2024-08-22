package com.example.garage.Entity;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @NotBlank(message = "vehicleName cannot be empty")
    private String vehicleName;

    @NotNull(message = "vehicleModel cannot be empty")
    private String vehicleModel;  

    @NotBlank(message = "vehicleNumber cannot be empty")
    private String vehicleNumber;
    
    @NotBlank(message = "price cannot be empty")
    private String price;

    @ElementCollection
    @Lob
    @JsonIgnore
    private List<Blob> images = new ArrayList<>();
}
