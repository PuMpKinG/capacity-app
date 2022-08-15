package de.chu.capacityapp.server.service;

import de.chu.capacityapp.entity.model.Vehicle;
import de.chu.capacityapp.entity.model.VehicleState;
import de.chu.capacityapp.entity.model.VehicleUsage;
import de.chu.capacityapp.server.repository.VehicleRepository;
import de.chu.capacityapp.server.repository.VehicleUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleUsageService {

    private final VehicleUsageRepository vehicleUsageRepository;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleUsageService(VehicleUsageRepository vehicleUsageRepository, VehicleRepository vehicleRepository) {
        this.vehicleUsageRepository = vehicleUsageRepository;
        this.vehicleRepository = vehicleRepository;
    }


    /**
     * Gibt alle im Bestand vorhandenen Fahrzeuge zurück.
     *
     * @return Fahrzeuge
     */
    public List<VehicleUsage> getAllVehicleUsages() {
        return vehicleUsageRepository.findAll();
    }

    /**
     * Legt ein neues Fahrzeug im Bestand an (neues Fahrzeug gekauft)
     *
     * @param usage
     * @return
     */
    public VehicleUsage createNewVehicleUsage(VehicleUsage usage) {
        String errorMsg = validateField(usage);
        if (errorMsg != null) {
            throw new IllegalStateException(errorMsg);
        }

        Optional<VehicleUsage> exists = vehicleUsageRepository.findVehicleUsageByLisencePlate(usage.getLisencePlate());
        if (exists.isPresent()) {
            throw new IllegalStateException("Vehicle is already registered in company");
        }

        Optional<Vehicle> vehicle = vehicleRepository.findById(usage.getVehicleRefObj().getId());
        if (vehicle.isEmpty()) {
            throw new IllegalStateException("Vehicle model does not exists");
        }

        usage.setVehicleRefObj(vehicle.get());
        usage.setUsedCapacity(usage.getUsedCapacity() != null ? usage.getUsedCapacity() : 0.0);
        usage.setVehicleState(VehicleState.AVAILABLE);
        return vehicleUsageRepository.save(usage);
    }

    @Transactional
    public void loadGoodsOnVehicle(Long vehicleUsageId, Double ldm) {
        VehicleUsage foundUsage = getVehicleUsage(vehicleUsageId);

        double usedCapacity = foundUsage.getUsedCapacity() + ldm;
        double maxCapacity = foundUsage.getVehicleRefObj().getCapacity();

        if (foundUsage.getVehicleRefObj().getCapacity() < usedCapacity) {
            throw new IllegalStateException("Vehicle will be overloaded: " + usedCapacity + " / " + maxCapacity);
        }

        foundUsage.setUsedCapacity(usedCapacity);
        foundUsage.setLoadingTime(Timestamp.from(Instant.now()));
        // wenn beladen wird, setzte alte Entladezeit zurück
        foundUsage.setUnloadingTime(null);
    }

    @Transactional
    public void unloadGoodsFromVehicle(Long vehicleUsageId, Double ldm) {
        VehicleUsage foundUsage = getVehicleUsage(vehicleUsageId);

        if (foundUsage.getUsedCapacity() == 0) {
            throw new IllegalStateException("Vehicle is empty, nothing to unload");
        }

        foundUsage.setUsedCapacity(0.0);
        foundUsage.setUnloadingTime(Timestamp.from(Instant.now()));
        // wenn entladen wird, setzte alte Beladezeit zurück
        foundUsage.setLoadingTime(null);
    }

    @Transactional
    public void changeState(Long vehicleUsageId, VehicleState state) {
        VehicleUsage foundUsage = getVehicleUsage(vehicleUsageId);
        foundUsage.setVehicleState(state);
    }

    public void deleteVehicleUsage(Long vehicleUsageId) {
        boolean exists = vehicleUsageRepository.existsById(vehicleUsageId);

        if (!exists) {
            throw new IllegalStateException("Vehicle does not exists");
        }

        vehicleUsageRepository.deleteById(vehicleUsageId);
    }

    private VehicleUsage getVehicleUsage(Long vehicleUsageId) {
        return vehicleUsageRepository.findById(vehicleUsageId)
                .orElseThrow(() -> new IllegalStateException("Vehicle not found on company: " + vehicleUsageId));
    }

    private String validateField(VehicleUsage usage) {
        if (usage.getLisencePlate() == null || usage.getLisencePlate().isEmpty()) {
            return "license can not be empty";
        } else if (usage.getVehicleRefObj() == null || usage.getVehicleRefObj().getId() == null) {
            return "vehicle model unknown";
        } else {
            return null;
        }
    }


}
