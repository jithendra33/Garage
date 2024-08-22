package com.example.garage.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.garage.Entity.VehicleEntity;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
    List<VehicleEntity> findByVehicleName(String vehicleName);
    Optional<VehicleEntity> findById(Long id);
    
    
    List<VehicleEntity> findByVehicleModel(String vehicleModel);
	Optional<VehicleEntity> findByVehicleNumber(String vehicleNumber);
    
}