package de.chu.capacityapp.entity.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Klasse für den Autotyp: mit Hersteller und Modelangabe, sowie der Referenz zur Ladegröße
 */
@Entity
@Table(name = "vehicle")
@SequenceGenerator(name = "vehicle_type_sequence", sequenceName = "vehicle_type_sequence", allocationSize = 1)
public class Vehicle extends AbstractEntity {
    @Column(name = "company", unique = true)
    private String company;

    @Column(name = "model", unique = true)
    private String model;

    @Column(name = "length", nullable = false)
    private BigDecimal length;

    @Column(name = "width", nullable = false)
    private BigDecimal width;

    @Column(name = "height", nullable = false)
    private BigDecimal height;

    @Transient
    private Long capacity;

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    /**
     * Ein Beispiel: ein Sattelauflieger ist standardmäßig 13,6 m lang, ~ 2,7 m hoch und ~ 2,4 m breit.
     * Unabhängig von der eingenommenen Höhe und Breite der Ladung ist dabei ein Lademeter immer ein Meter in Länge der
     * Ladefläche. Die Anzahl der LKW Lademeter entspricht also immer der Länge der Ladefläche
     * – er hat folglich 13,6 Lademeter (ldm).
     * @return
     */
    public Long getCapacity() {
        return this.getLength().longValue();
    }

    public Vehicle() {
    }

    public Vehicle(String company, String model, BigDecimal length, BigDecimal width, BigDecimal height) {
        this.company = company;
        this.model = model;
        this.length = length;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(company, vehicle.company) && Objects.equals(model, vehicle.model) && Objects.equals(length, vehicle.length) && Objects.equals(width, vehicle.width) && Objects.equals(height, vehicle.height) && Objects.equals(capacity, vehicle.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), company, model, length, width, height, capacity);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "company='" + company + '\'' +
                ", model='" + model + '\'' +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", quantity=" + capacity +
                ", id=" + id +
                '}';
    }
}
