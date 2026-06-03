package fr.diginamic.echolink.application.location.service;

import fr.diginamic.echolink.application.location.port.in.LocationSyncUseCase;
import fr.diginamic.echolink.application.location.port.out.LocationProvider;
import fr.diginamic.echolink.application.location.port.out.LocationRepository;
import fr.diginamic.echolink.domain.location.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImportLocationService implements LocationSyncUseCase {

    private final LocationProvider provider;
    private final LocationRepository repository;

    public void syncLocations() {

        long currentTimeMillis = System.currentTimeMillis();
        log.info("Location sync started");

        Set<String> existingInseeCodes = repository.findAllInseeCodes();

        List<Location> newLocations = provider.getAllLocations().stream()
                .filter(location -> location.getInseeCode() != null)
                .filter(location -> !existingInseeCodes.contains(location.getInseeCode()))
                .toList();

        repository.saveAll(newLocations);

        log.info("{} Locations synchronised in {}s",
                newLocations.size(),
                String.format("%.2f", (System.currentTimeMillis() - currentTimeMillis) / 1000d));

    }
}
