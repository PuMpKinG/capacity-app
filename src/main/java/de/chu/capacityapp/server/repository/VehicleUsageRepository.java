package de.chu.capacityapp.server.repository;

import de.chu.capacityapp.entity.model.VehicleUsage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleUsageRepository extends JpaRepository<VehicleUsage, Long> {

}
