package com.example.garage.UserVehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingVehicleRepository extends JpaRepository<PendingVehicleEntity, Long> {
}