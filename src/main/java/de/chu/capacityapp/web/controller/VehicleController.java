package de.chu.capacityapp.web.controller;

import de.chu.capacityapp.entity.dto.VehicleDTO;
import de.chu.capacityapp.entity.dto.VehicleUsageDTO;
import de.chu.capacityapp.entity.model.Vehicle;
import de.chu.capacityapp.entity.model.VehicleUsage;
import de.chu.capacityapp.server.service.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;
    private final ModelMapper modelMapper;

    @Autowired
    public VehicleController(VehicleService vehicleService, ModelMapper modelMapper) {
        this.vehicleService = vehicleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<VehicleDTO> getVehicles() {
        return this.vehicleService.getAllVehicles().stream()
                .map(this::convertToDto).collect(Collectors.toList());
    }

    @PostMapping
    public VehicleDTO createVehicle(@RequestBody VehicleDTO vehicleDTO) {
        return convertToDto(
                this.vehicleService.createNewVehicle(
                        convertToModel(vehicleDTO)));
    }

    @PutMapping(path = "/update/{vehicleId}/")
    public void updateVehicle(@PathVariable("vehicleId") Long vehicleId,
                              @RequestBody VehicleDTO vehicleDTO) {
        Vehicle update = convertToModel(vehicleDTO);
        this.vehicleService.updateVehicle(update, vehicleId);
    }

    @DeleteMapping(path = "/delete/{vehicleId}")
    public void deleteVehicle(@PathVariable("vehicleId") Long vehicleId) {
        this.vehicleService.deleteVehicle(vehicleId);
    }


    private VehicleDTO convertToDto(Vehicle vehicle){
        return modelMapper.map(vehicle, VehicleDTO.class);
    }

    private Vehicle convertToModel(VehicleDTO dto) {
        return modelMapper.map(dto, Vehicle.class);
    }
}
