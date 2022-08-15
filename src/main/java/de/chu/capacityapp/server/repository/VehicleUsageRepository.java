package de.chu.capacityapp.server.repository;

import de.chu.capacityapp.entity.model.VehicleState;
import de.chu.capacityapp.entity.model.VehicleUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VehicleUsageRepository extends JpaRepository<VehicleUsage, Long> {

    @Query("SELECT vu FROM VehicleUsage vu WHERE vu.lisencePlate = ?1")
    Optional<VehicleUsage> findVehicleUsageByLisencePlate(String licesencePlate);

    // FIXME: Without parameter possible?
    @Query("SELECT vu FROM VehicleUsage vu WHERE (vu.vehicleRefObj.length - vu.usedCapacity) > 0 and vu.vehicleState = ?1")
    Optional<List<VehicleUsage>> findAllByFilterOnAvailable(VehicleState state);
}
