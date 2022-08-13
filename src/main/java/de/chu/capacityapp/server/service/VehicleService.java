package de.chu.capacityapp.server.service;

import de.chu.capacityapp.entity.model.Vehicle;
import de.chu.capacityapp.entity.model.VehicleUsage;
import de.chu.capacityapp.server.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle createNewVehicle(Vehicle vehicle) {
        boolean exists = vehicleRepository.existsById(vehicle.getId());

        if (exists) {
            throw new IllegalStateException("Vehicle Model exists");
        }

        return vehicleRepository.save(vehicle);
    }

    @Transactional
    public void updateVehicle(Vehicle update, Long vehicleId) {
        Vehicle found = vehicleRepository.findById(vehicleId).orElseThrow(() -> new IllegalStateException("Vehicle not found"));

        if (update.getCompany() == null && update.getCompany().isEmpty()) {
            throw new IllegalStateException("Company must be filled");
        }
        if (update.getModel() == null && update.getModel().isEmpty()) {
            throw new IllegalStateException("Model must be filled");
        }
        if (update.getLength() == null) {
            throw new IllegalStateException("Company must be filled");
        }
        if (update.getHeight() == null ) {
            throw new IllegalStateException("Company must be filled");
        }
        if (update.getWidth() == null ) {
            throw new IllegalStateException("Company must be filled");
        }

        found.setCompany(update.getCompany());
        found.setModel(update.getModel());
        found.setLength(update.getLength());
        found.setHeight(update.getHeight());
        found.setWidth(update.getWidth());
    }

    public void deleteVehicle(Long vehicleId) {
        boolean exists = vehicleRepository.existsById(vehicleId);

        if (!exists) {
            throw new IllegalStateException("Vehicle does not exists");
        }

        vehicleRepository.deleteById(vehicleId);
    }
}
