package de.chu.capacityapp.configuration;

import de.chu.capacityapp.entity.model.Vehicle;
import de.chu.capacityapp.entity.model.VehicleState;
import de.chu.capacityapp.entity.model.VehicleUsage;
import de.chu.capacityapp.server.repository.VehicleRepository;
import de.chu.capacityapp.server.repository.VehicleUsageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class InitDatabaseConfig {

    @Bean
    CommandLineRunner commandLineRunner(VehicleRepository vehicleRepository, VehicleUsageRepository vehicleUsageRepository) {
        return args -> {
            Vehicle mercedesSprinter = new Vehicle("Mercedes", "Sprinter", 2.6, 1.4, 1.6);
            Vehicle mercedesArcos = new Vehicle("Mercedes", "Arocs", 13.6, 2.4, 2.7);
            Vehicle mercedesArcos2 = new Vehicle("Mercedes", "Arocs + Anhänger", 27.2, 2.4, 2.7);
            Vehicle hyzon = new Vehicle("Hyzon ", "Road Train", 50.0, 2.4, 2.7);
            Vehicle ford = new Vehicle("Ford ", "Galaxy", 2.04, 1.01, 0.8);

            vehicleRepository.saveAll(List.of(mercedesSprinter, mercedesArcos, mercedesArcos2, hyzon, ford));

            VehicleUsage mercedesSprinterOne = new VehicleUsage("ME SP 122", null, null, 0.0, VehicleState.AVAILABLE, mercedesSprinter);
            VehicleUsage mercedesSprinterTwo = new VehicleUsage("ME SP 153", null, null, 0.0, VehicleState.AVAILABLE, mercedesSprinter);
            VehicleUsage mercedesSprinterThree = new VehicleUsage("ME SP 198", null, null, 0.0, VehicleState.AVAILABLE, mercedesSprinter);

            VehicleUsage mercedesArcosOne = new VehicleUsage("ME AC 213", null, null, 0.0, VehicleState.AVAILABLE, mercedesArcos);
            VehicleUsage mercedesArcosTwo = new VehicleUsage("ME AC 245", null, null, 0.0, VehicleState.AVAILABLE, mercedesArcos);
            VehicleUsage mercedesArcosLong = new VehicleUsage("ME AC 243", null, null, 0.0, VehicleState.AVAILABLE, mercedesArcos2);

            VehicleUsage hyzonOne = new VehicleUsage("HY ZO 001", null, null, 0.0, VehicleState.AVAILABLE, hyzon);

            VehicleUsage galaxyOne = new VehicleUsage("GA LA 77", null, null, 0.0, VehicleState.AVAILABLE, hyzon);
            VehicleUsage galaxyTwo = new VehicleUsage("GA LA 645", null, null, 0.0, VehicleState.AVAILABLE, hyzon);

            vehicleUsageRepository.saveAll(
                    List.of(mercedesSprinterOne,
                            mercedesSprinterTwo,
                            mercedesSprinterThree,
                            mercedesArcosOne,
                            mercedesArcosTwo,
                            mercedesArcosLong,
                            hyzonOne,
                            galaxyOne,
                            galaxyTwo
                    ));
        };
    }

}
