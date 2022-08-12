package de.chu.capacityapp.server.service;

import de.chu.capacityapp.entity.model.VehicleUsage;
import de.chu.capacityapp.server.repository.VehicleUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleUsageService {

    private final VehicleUsageRepository vehicleUsageRepository;

    @Autowired
    public VehicleUsageService(VehicleUsageRepository vehicleUsageRepository) {
        this.vehicleUsageRepository = vehicleUsageRepository;
    }


    /**
     * Gibt alle im Bestand vorhandenen Fahrzeuge zurück.
     *
     * @return
     */
    public List<VehicleUsage> getAllVehicleUsages() {
        return vehicleUsageRepository.findAll();
    }

    public VehicleUsage createNewVehicle(VehicleUsage convertToModel) {

    }
}
