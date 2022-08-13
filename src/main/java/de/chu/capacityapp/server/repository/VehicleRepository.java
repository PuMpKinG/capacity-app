package de.chu.capacityapp.server.repository;

import de.chu.capacityapp.entity.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("SELECT v FROM Vehicle v WHERE v.company = ?1 and v.model = ?2")
    Optional<Vehicle> findVehicleByCompanyAndModel(String company, String model);
}
