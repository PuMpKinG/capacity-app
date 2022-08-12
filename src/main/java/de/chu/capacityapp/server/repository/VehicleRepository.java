package de.chu.capacityapp.server.repository;

import de.chu.capacityapp.entity.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
