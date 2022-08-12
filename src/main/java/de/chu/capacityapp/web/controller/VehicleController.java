package de.chu.capacityapp.web.controller;

import de.chu.capacityapp.entity.dto.VehicleDTO;
import de.chu.capacityapp.entity.model.Vehicle;
import de.chu.capacityapp.server.service.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private VehicleDTO convertToDto(Vehicle vehicle){
        return modelMapper.map(vehicle, VehicleDTO.class);
    }
}
