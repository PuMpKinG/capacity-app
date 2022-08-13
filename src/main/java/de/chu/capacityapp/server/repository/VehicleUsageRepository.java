package de.chu.capacityapp.server.repository;

import de.chu.capacityapp.entity.model.VehicleUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VehicleUsageRepository extends JpaRepository<VehicleUsage, Long> {

    @Query("SELECT vu FROM VehicleUsage vu WHERE vu.lisencePlate = ?1")
    Optional<VehicleUsage> findVehicleUsageByLisencePlate(String licesencePlate);
}
