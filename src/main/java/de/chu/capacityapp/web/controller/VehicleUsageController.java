package de.chu.capacityapp.web.controller;

import de.chu.capacityapp.entity.dto.VehicleUsageDTO;
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
    public List<VehicleUsageDTO> getVehicles() {
        return this.vehicleUsageService.getAllVehicleUsages().stream()
                .map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Erstellt eine neue VehicleUsage, es wurde also ein neues Fahrzeug erworben
     *
     * @param vehicleUsageDTO
     * @return
     */
    @PostMapping
    public VehicleUsageDTO createNewVehicleUsage(@RequestBody VehicleUsageDTO vehicleUsageDTO) {
        return convertToDto(
                this.vehicleUsageService.createNewVehicleUsage(
                        convertToModel(vehicleUsageDTO)));
    }

    @PutMapping(path = "/{vehicleUsageId}/load/{ldm}")
    public void loadGoodsOnVehicle(@PathVariable("vehicleUsageId") Long vehicleUsageId,
                            @PathVariable("ldm") Double ldm) {
        this.vehicleUsageService.loadGoodsOnVehicle(vehicleUsageId, ldm);
    }

    @PutMapping(path = "/{vehicleUsageId}/unload/{ldm}")
    public void unloadGoodsFromVehicle(@PathVariable("vehicleUsageId") Long vehicleUsageId,
                              @PathVariable("ldm") Double ldm) {
        this.vehicleUsageService.unloadGoodsFromVehicle(vehicleUsageId, ldm);
    }

    @PutMapping(path = "/{vehicleUsageId}/state/{state}")
    public void changeState(@PathVariable("vehicleUsageId") Long vehicleUsageId,
                            @PathVariable("state") VehicleState state) {
        this.vehicleUsageService.changeState(vehicleUsageId, state);
    }

    @DeleteMapping(path = "/delete/{vehicleUsageId}")
    public void deleteVehicleUsage(@PathVariable("vehicleUsageId") Long vehicleUsageId) {
        this.vehicleUsageService.deleteVehicleUsage(vehicleUsageId);
    }

    private VehicleUsageDTO convertToDto(VehicleUsage vehicleUsage) {
        return modelMapper.map(vehicleUsage, VehicleUsageDTO.class);
    }

    private VehicleUsage convertToModel(VehicleUsageDTO dto) {
        return modelMapper.map(dto, VehicleUsage.class);
    }
}
