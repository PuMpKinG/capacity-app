package de.chu.capacityapp.entity.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class VehicleUsageDTO {
    private Long id;
    private String lisencePlate;
    private Timestamp loadingTime;
    private Timestamp unloadingTime;
    private Double usedCapacity;
    private String vehicleState;
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

    public Double getUsedCapacity() {
        return usedCapacity;
    }

    public void setUsedCapacity(Double usedCapacity) {
        this.usedCapacity = usedCapacity;
    }

    public String getVehicleState() {
        return vehicleState;
    }

    public void setVehicleState(String vehicleState) {
        this.vehicleState = vehicleState;
    }

    public VehicleDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }
}
