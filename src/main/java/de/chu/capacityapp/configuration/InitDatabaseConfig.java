package de.chu.capacityapp.configuration;

import de.chu.capacityapp.entity.model.Vehicle;
import de.chu.capacityapp.entity.model.VehicleStatus;
import de.chu.capacityapp.entity.model.VehicleUsage;
import de.chu.capacityapp.server.repository.VehicleRepository;
import de.chu.capacityapp.server.repository.VehicleUsageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class InitDatabaseConfig {

    @Bean
    CommandLineRunner commandLineRunner(VehicleRepository vehicleRepository, VehicleUsageRepository vehicleUsageRepository) {
        return args -> {
            Vehicle mercedesSprinter = new Vehicle("Mercedes", "Sprinter", new BigDecimal("2.6"), new BigDecimal("1.4"), new BigDecimal("1.6"));
            Vehicle mercedesArcos = new Vehicle("Mercedes", "Arocs", new BigDecimal("13.6"), new BigDecimal("2.4"), new BigDecimal("2.7"));
            Vehicle mercedesArcos2 = new Vehicle("Mercedes", "Arocs + Anhänger", new BigDecimal("27.2"), new BigDecimal("2.4"), new BigDecimal("2.7"));
            Vehicle hyzon = new Vehicle("Hyzon ", "Road Train", new BigDecimal("50.0"), new BigDecimal("2.4"), new BigDecimal("2.7"));
            Vehicle ford = new Vehicle("Ford ", "Galaxy", new BigDecimal("2.04"), new BigDecimal("1.01"), new BigDecimal("0.8"));

            vehicleRepository.saveAll(List.of(mercedesSprinter, mercedesArcos, mercedesArcos2, hyzon, ford));

            VehicleUsage mercedesSprinterOne = new VehicleUsage("ME SP 122", null, null, 0L, VehicleStatus.AVAILABLE, mercedesSprinter);
            VehicleUsage mercedesSprinterTwo = new VehicleUsage("ME SP 153", null, null, 0L, VehicleStatus.AVAILABLE, mercedesSprinter);
            VehicleUsage mercedesSprinterThree = new VehicleUsage("ME SP 198", null, null, 0L, VehicleStatus.AVAILABLE, mercedesSprinter);

            VehicleUsage mercedesArcosOne = new VehicleUsage("ME AC 213", null, null, 0L, VehicleStatus.AVAILABLE, mercedesArcos);
            VehicleUsage mercedesArcosTwo = new VehicleUsage("ME AC 245", null, null, 0L, VehicleStatus.AVAILABLE, mercedesArcos);
            VehicleUsage mercedesArcosLong = new VehicleUsage("ME AC 243", null, null, 0L, VehicleStatus.AVAILABLE, mercedesArcos2);

            VehicleUsage hyzonOne = new VehicleUsage("HY ZO 001", null, null, 0L, VehicleStatus.AVAILABLE, hyzon);

            VehicleUsage galaxyOne = new VehicleUsage("GA LA 77", null, null, 0L, VehicleStatus.AVAILABLE, hyzon);
            VehicleUsage galaxyTwo = new VehicleUsage("GA LA 645", null, null, 0L, VehicleStatus.AVAILABLE, hyzon);

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
