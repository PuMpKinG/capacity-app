package de.chu.capacityapp.server.service;

import de.chu.capacityapp.entity.model.Vehicle;
import de.chu.capacityapp.entity.model.VehicleState;
import de.chu.capacityapp.entity.model.VehicleUsage;
import de.chu.capacityapp.server.error.CapacityAppError;
import de.chu.capacityapp.server.error.CapacityAppException;
import de.chu.capacityapp.server.repository.VehicleRepository;
import de.chu.capacityapp.server.repository.VehicleUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static de.chu.capacityapp.server.error.CapacityAppError.USAGE_EXISTS;
import static de.chu.capacityapp.server.error.CapacityAppError.NOT_FOUND;

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
    public List<VehicleUsage> getAllVehicleUsages(boolean filterOnAvailable) {
        if (filterOnAvailable) {
            return vehicleUsageRepository.findAllByFilterOnAvailable(VehicleState.AVAILABLE).orElseGet(ArrayList::new);
        }
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
            throw new CapacityAppException(errorMsg);
        }

        Optional<VehicleUsage> exists = vehicleUsageRepository.findVehicleUsageByLisencePlate(usage.getLisencePlate());
        if (exists.isPresent()) {
            throw new CapacityAppException(USAGE_EXISTS);
        }

        Optional<Vehicle> vehicle = vehicleRepository.findById(usage.getVehicleRefObj().getId());
        if (vehicle.isEmpty()) {
            throw new CapacityAppException(NOT_FOUND);
        }

        usage.setVehicleRefObj(vehicle.get());
        usage.setUsedCapacity(usage.getUsedCapacity() != null ? usage.getUsedCapacity() : 0.0);
        usage.setVehicleState(VehicleState.AVAILABLE);
        return vehicleUsageRepository.save(usage);
    }

    @Transactional
    public VehicleUsage loadGoodsOnVehicle(Long vehicleUsageId, Double ldm) {
        VehicleUsage foundUsage = getVehicleUsage(vehicleUsageId);

        double usedCapacity = foundUsage.getUsedCapacity() + ldm;
        double maxCapacity = foundUsage.getVehicleRefObj().getCapacity();

        if (foundUsage.getVehicleRefObj().getCapacity() < usedCapacity) {
            throw new CapacityAppException(
                    CapacityAppError.USAGE_OVERLOAD,
                    usedCapacity,
                    maxCapacity);
        }

        foundUsage.setUsedCapacity(usedCapacity);
        foundUsage.setLoadingTime(Timestamp.from(Instant.now()));
        // wenn beladen wird, setzte alte Entladezeit zurück
        foundUsage.setUnloadingTime(null);

        return foundUsage;
    }

    @Transactional
    public VehicleUsage unloadGoodsFromVehicle(Long vehicleUsageId, Double ldm) {
        VehicleUsage foundUsage = getVehicleUsage(vehicleUsageId);

        if (foundUsage.getUsedCapacity() == 0) {
            throw new CapacityAppException(CapacityAppError.USAGE_EMPTY);
        }

        if (foundUsage.getUsedCapacity() < ldm) {
            throw new CapacityAppException(CapacityAppError.USAGE_TO_LESS, foundUsage.getUsedCapacity(), ldm);
        }

        foundUsage.setUsedCapacity(foundUsage.getUsedCapacity() - ldm);
        foundUsage.setUnloadingTime(Timestamp.from(Instant.now()));
        // wenn entladen wird, setzte alte Beladezeit zurück
        foundUsage.setLoadingTime(null);

        return foundUsage;
    }

    @Transactional
    public VehicleUsage changeState(Long vehicleUsageId, VehicleState state) {
        VehicleUsage foundUsage = getVehicleUsage(vehicleUsageId);
        foundUsage.setVehicleState(state);

        return foundUsage;
    }

    public void deleteVehicleUsage(Long vehicleUsageId) {
        boolean exists = vehicleUsageRepository.existsById(vehicleUsageId);

        if (!exists) {
            throw new CapacityAppException(NOT_FOUND);
        }

        vehicleUsageRepository.deleteById(vehicleUsageId);
    }

    private VehicleUsage getVehicleUsage(Long vehicleUsageId) {
        return vehicleUsageRepository.findById(vehicleUsageId)
                .orElseThrow(() ->  new CapacityAppException(NOT_FOUND));
    }

    private String validateField(VehicleUsage usage) {
        if (usage.getLisencePlate() == null || usage.getLisencePlate().isEmpty()) {
            return "Bitte Kennzeichen angeben ";
        } else if (usage.getVehicleRefObj() == null || usage.getVehicleRefObj().getId() == null) {
            return "Fahrzeugmodell wurde nicht gefunden";
        } else {
            return null;
        }
    }


}
