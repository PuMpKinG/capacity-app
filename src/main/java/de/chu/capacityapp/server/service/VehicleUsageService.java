package de.chu.capacityapp.server.service;

import de.chu.capacityapp.entity.model.VehicleState;
import de.chu.capacityapp.entity.model.VehicleUsage;
import de.chu.capacityapp.server.repository.VehicleUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public VehicleUsage createNewVehicleUsage(VehicleUsage usage) {
        boolean exists = vehicleUsageRepository.existsById(usage.getId());

        if (exists) {
            throw new IllegalStateException("Vehicle is already registered in company");
        }

        return vehicleUsageRepository.save(usage);
    }

    // TODO: Muss das @Transactional immer an jeder Methode geschrieben werden, oder gibt es eine andere Lösung?
    @Transactional
    public void loadGoodsOnVehicle(Long vehicleUsageId, Double ldm) {
        VehicleUsage foundUsage = getVehicleUsage(vehicleUsageId);

        double usedCapacity = foundUsage.getUsedCapacity() + ldm;
        double maxCapacity = foundUsage.getVehicleRefObj().getCapacity();

        if (foundUsage.getVehicleRefObj().getCapacity() < usedCapacity) {
            throw new IllegalStateException("Vehicle will be overloaded: " + usedCapacity + " / " + maxCapacity);
        }

        foundUsage.setUsedCapacity(usedCapacity);
    }

    @Transactional
    public void unloadGoodsFromVehicle(Long vehicleUsageId, Double ldm) {
        VehicleUsage foundUsage = getVehicleUsage(vehicleUsageId);

        if (foundUsage.getUsedCapacity() == 0) {
            throw new IllegalStateException("Vehicle is empty, nothing to unload");
        }

        foundUsage.setUsedCapacity(0.0);
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


}
