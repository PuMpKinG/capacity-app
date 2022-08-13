package de.chu.capacityapp.entity.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Klasse für ein spezifisches Fahrzeug, welches sich im Bestand des Unternehmens befindet.
 */
@Entity
@Table(name = "vehicle_usage",  uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id", "license_plate"})
})
@SequenceGenerator(name = "vehicle_sequence", sequenceName = "vehicle_sequence", allocationSize = 1)
public class VehicleUsage extends AbstractEntity {

    @Column(name = "license_plate", length = 20, unique = true)
    private String lisencePlate;
    @Column(name = "loading_time")
    private Timestamp loadingTime;
    @Column(name = "unloading_time")
    private Timestamp unloadingTime;
    @Column(name = "used_capacity", nullable = false )
    private Double usedCapacity = 0.0;
    @Column(name = "vehicle_state", nullable = false)
    private VehicleState vehicleState;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_ref")
    private Vehicle vehicleRefObj;


    public String getLisencePlate() {
        return lisencePlate;
    }

    public void setLisencePlate(String lisence) {
        this.lisencePlate = lisence;
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

    public VehicleState getVehicleState() {
        return vehicleState;
    }

    public void setVehicleState(VehicleState vehicleState) {
        this.vehicleState = vehicleState;
    }

    public Vehicle getVehicleRefObj() {
        return vehicleRefObj;
    }

    public void setVehicleRefObj(Vehicle vehicleRefObj) {
        this.vehicleRefObj = vehicleRefObj;
    }

    public VehicleUsage() {
    }

    public VehicleUsage(String lisencePlate, Timestamp loadingTime, Timestamp unloadingTime, Double usedCapacity, VehicleState vehicleState, Vehicle vehicleRefObj) {
        this.lisencePlate = lisencePlate;
        this.loadingTime = loadingTime;
        this.unloadingTime = unloadingTime;
        this.usedCapacity = usedCapacity;
        this.vehicleState = vehicleState;
        this.vehicleRefObj = vehicleRefObj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VehicleUsage that = (VehicleUsage) o;
        return Objects.equals(lisencePlate, that.lisencePlate) && Objects.equals(loadingTime, that.loadingTime) && Objects.equals(unloadingTime, that.unloadingTime) && Objects.equals(usedCapacity, that.usedCapacity) && vehicleState == that.vehicleState && Objects.equals(vehicleRefObj, that.vehicleRefObj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), lisencePlate, loadingTime, unloadingTime, usedCapacity, vehicleState, vehicleRefObj);
    }

    @Override
    public String toString() {
        return "VehicleUsage{" +
                "lisencePlate='" + lisencePlate + '\'' +
                ", loadingTime=" + loadingTime +
                ", unloadingTime=" + unloadingTime +
                ", usedCapacity=" + usedCapacity +
                ", vehicleStatus=" + vehicleState +
                ", vehicleRefObj=" + vehicleRefObj +
                ", id=" + id +
                '}';
    }
}
