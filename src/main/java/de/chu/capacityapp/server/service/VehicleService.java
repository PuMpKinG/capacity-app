package de.chu.capacityapp.server.service;

import de.chu.capacityapp.entity.model.Vehicle;
import de.chu.capacityapp.server.error.CapacityAppError;
import de.chu.capacityapp.server.error.CapacityAppException;
import de.chu.capacityapp.server.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Lädt alle Fahrzeugmodelle aus der Datenbank
     *
     * @return gefundene Fahrzeugmodelle
     */
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    /**
     * Erstellt ein neues Fahrzeugmodell in der Datenbank
     *
     * @param vehicle
     * @return neu erstelltes Fahrzeugmodell mit autogen. id.
     */
    public Vehicle createNewVehicle(Vehicle vehicle) {
        String errorMsg = validateFields(vehicle);
        if (errorMsg != null) {
            throw new IllegalStateException(errorMsg);
        }

        Optional<Vehicle> exists = vehicleRepository.findVehicleByCompanyAndModel(vehicle.getCompany(), vehicle.getModel());
        if (exists.isPresent()) {
            throw new CapacityAppException(CapacityAppError.VEHICLE_EXISTS);
        }

        return vehicleRepository.save(vehicle);
    }

    /**
     * Aktualisiert ein Fahrzeugmodell in der Datenbank.
     *
     * @param update
     * @param vehicleId
     */
    @Transactional
    public Vehicle updateVehicle(Vehicle update, Long vehicleId) {
        Vehicle found = vehicleRepository.findById(vehicleId).orElseThrow(() -> new CapacityAppException(CapacityAppError.NOT_FOUND));

        String errorMsg = validateFields(update);
        if (errorMsg != null) {
            throw new IllegalStateException(errorMsg);
        }

        found.setCompany(update.getCompany());
        found.setModel(update.getModel());
        found.setLength(update.getLength());
        found.setHeight(update.getHeight());
        found.setWidth(update.getWidth());

        return found;
    }

    /**
     * Löscht ein Fahrzeugmodell aus der Datenbank
     *
     * @param vehicleId
     */
    public void deleteVehicle(Long vehicleId) {
        Optional<Vehicle> exists = vehicleRepository.findById(vehicleId);

        if (exists.isEmpty()) {
            throw new CapacityAppException(CapacityAppError.NOT_FOUND);
        }

        if (exists.get().getVehicleUsagesByVehicleRef().size() > 0) {
            throw new CapacityAppException(CapacityAppError.VEHICLE_USED);
        }

        vehicleRepository.deleteById(vehicleId);
    }

    private String validateFields(Vehicle update) {
        if (update.getCompany() == null  || update.getCompany().isEmpty()) {
            return"Bitte Hersteller angeben";
        } else if (update.getModel() == null || update.getModel().isEmpty()) {
            return"Bitte Modell angeben";
        } else if (update.getLength() == null || update.getHeight() == null || update.getWidth() == null) {
            return"Bitte Länge, Breite, Höhe angeben";
        } else {
            return null;
        }
    }
}
