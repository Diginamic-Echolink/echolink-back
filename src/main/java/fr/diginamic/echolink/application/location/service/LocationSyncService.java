package fr.diginamic.echolink.application.location.service;

import fr.diginamic.echolink.application.location.port.in.LocationSyncUseCase;
import fr.diginamic.echolink.application.location.port.out.LocationProvider;
import fr.diginamic.echolink.application.location.port.out.LocationRepository;
import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.domain.location.exception.LocationApiSyncException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Service responsible for synchronizing location data from an external provider.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LocationSyncService implements LocationSyncUseCase {

    /**
     * Minimum population required for a location to be synchronized.
     */
    private static final long MIN_POPULATION = 10_000L;

    /**
     * Repository used to access and persist location data.
     */
    private final LocationRepository repository;

    /**
     * Provider used to retrieve location data from an external source.
     */
    private final LocationProvider provider;

    /**
     * Synchronizes locations from the external provider and persists new entries.
     */
    public void syncLocations() {

        long currentTimeMillis = System.currentTimeMillis();
        log.info("Location sync started");

        Set<String> existingInseeCodes = repository.getAllInseeCodes();
        List<Location> locations;

        try {

            locations = provider.getAllLocations();

        } catch (LocationApiSyncException ex) {
            log.warn("Unable to retrieve weather datas due to API failure.", ex);
            return;
        }

        List<Location> newLocations = locations.stream()
                .filter(location -> location.getInseeCode() != null)
                .filter(location -> !existingInseeCodes.contains(location.getInseeCode()))
                .filter(location -> location.getPopulation() > MIN_POPULATION)
                .toList();

        repository.saveAll(newLocations);

        log.info("{} Locations synchronised in {}s",
                existingInseeCodes.size() + newLocations.size(),
                String.format("%.2f", (System.currentTimeMillis() - currentTimeMillis) / 1000d)
        );
    }
}
