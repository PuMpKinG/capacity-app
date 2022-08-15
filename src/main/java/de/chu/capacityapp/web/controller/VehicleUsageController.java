package de.chu.capacityapp.web.controller;

import de.chu.capacityapp.entity.dto.VehicleDTO;
import de.chu.capacityapp.entity.dto.VehicleUsageDTO;
import de.chu.capacityapp.entity.model.Vehicle;
import de.chu.capacityapp.entity.model.VehicleState;
import de.chu.capacityapp.entity.model.VehicleUsage;
import de.chu.capacityapp.server.service.VehicleUsageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/vehicle-usage")
public class VehicleUsageController {

    private final VehicleUsageService vehicleUsageService;
    private final ModelMapper modelMapper;

    @Autowired
    public VehicleUsageController(VehicleUsageService vehicleUsageService, ModelMapper modelMapper) {
        this.vehicleUsageService = vehicleUsageService;
        this.modelMapper = modelMapper;
    }

    /**
     * Lade alle VehicleUsages, also alle Fahrzeuge die im Bestand des Unternehmens sind
     *
     * @return
     */
    @GetMapping
    public List<VehicleUsageDTO> getVehicles(@RequestParam(required = false, name = "filterOnAvailable") boolean filterOnAvailable) {
        return this.vehicleUsageService.getAllVehicleUsages(filterOnAvailable).stream()
                .map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Erstellt eine neue VehicleUsage, es wurde also ein neues Fahrzeug erworben
     *
     * @param vehicleUsageDTO
     * @return neues Fahrzeug im Bestand
     */
    @PostMapping
    public VehicleUsageDTO createNewVehicleUsage(@RequestBody VehicleUsageDTO vehicleUsageDTO) {
        return convertToDto(
                this.vehicleUsageService.createNewVehicleUsage(
                        convertToModel(vehicleUsageDTO)));
    }

    /**
     * Beladen des Fahrzeugs
     *
     * @param vehicleUsageId
     * @param ldm
     */
    @PutMapping(path = "/{vehicleUsageId}/load/{ldm}")
    public VehicleUsageDTO loadGoodsOnVehicle(@PathVariable("vehicleUsageId") Long vehicleUsageId,
                                              @PathVariable("ldm") Double ldm) {
        return convertToDto(
                this.vehicleUsageService.loadGoodsOnVehicle(vehicleUsageId, ldm));
    }

    /**
     * Entladen des Fahrzeugs
     *
     * @param vehicleUsageId
     * @param ldm
     */
    @PutMapping(path = "/{vehicleUsageId}/unload/{ldm}")
    public VehicleUsageDTO unloadGoodsFromVehicle(@PathVariable("vehicleUsageId") Long vehicleUsageId,
                                                  @PathVariable("ldm") Double ldm) {
        return convertToDto(
                this.vehicleUsageService.unloadGoodsFromVehicle(vehicleUsageId, ldm));
    }

    /**
     * Fahrzeug-Status aktualisieren: defekt, unterwegs oder im Lager
     *
     * @param vehicleUsageId
     * @param state
     */
    @PutMapping(path = "/{vehicleUsageId}/state/{state}")
    public VehicleUsageDTO changeState(@PathVariable("vehicleUsageId") Long vehicleUsageId,
                                       @PathVariable("state") VehicleState state) {
        return convertToDto(
                this.vehicleUsageService.changeState(vehicleUsageId, state));
    }

    /**
     * Löscht das Fahrzeug aus dem Bestand
     *
     * @param vehicleUsageId
     */
    @DeleteMapping(path = "/delete/{vehicleUsageId}")
    public void deleteVehicleUsage(@PathVariable("vehicleUsageId") Long vehicleUsageId) {
        this.vehicleUsageService.deleteVehicleUsage(vehicleUsageId);
    }

    private VehicleUsageDTO convertToDto(VehicleUsage vehicleUsage) {
        VehicleUsageDTO result = modelMapper.map(vehicleUsage, VehicleUsageDTO.class);
        result.setVehicleState(vehicleUsage.getVehicleState().name());
        result.setVehicle(modelMapper.map(vehicleUsage.getVehicleRefObj(), VehicleDTO.class));
        return result;
    }

    private VehicleUsage convertToModel(VehicleUsageDTO dto) {
        VehicleUsage result = modelMapper.map(dto, VehicleUsage.class);
        if (dto.getVehicleState() != null) {
            result.setVehicleState(VehicleState.valueOf(dto.getVehicleState()));
        }
        if (dto.getVehicle() != null) {
            result.setVehicleRefObj(modelMapper.map(dto.getVehicle(), Vehicle.class));
        }
        return result;
    }
}
