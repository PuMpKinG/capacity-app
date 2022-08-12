package de.chu.capacityapp.entity.dto;

import de.chu.capacityapp.entity.model.VehicleStatus;

import java.sql.Timestamp;

public class VehicleUsageDTO {
    private Long id;
    private String lisencePlate;
    private Timestamp loadingTime;
    private Timestamp unloadingTime;
    private Long usedCapacity;
    private VehicleStatus vehicleStatus;
    private VehicleDTO vehicle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLisencePlate() {
        return lisencePlate;
    }

    public void setLisencePlate(String lisencePlate) {
        this.lisencePlate = lisencePlate;
    }

    public Timestamp getLoadingTime() {
        return loadingTime;
    }

    public void setLoadingTime(Timestamp loadingTime) {
        this.loadingTime = loadingTime;
    }

    public Timestamp getUnloadingTime() {
        return unloadingTime;
    }

    public void setUnloadingTime(Timestamp unloadingTime) {
        this.unloadingTime = unloadingTime;
    }

    public Long getUsedCapacity() {
        return usedCapacity;
    }

    public void setUsedCapacity(Long usedCapacity) {
        this.usedCapacity = usedCapacity;
    }

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public VehicleDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }
}
