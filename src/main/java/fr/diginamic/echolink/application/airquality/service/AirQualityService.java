package fr.diginamic.echolink.application.airquality.service;

import fr.diginamic.echolink.application.airquality.port.in.AirQualityGetUseCase;
import fr.diginamic.echolink.application.airquality.port.out.AirQualityRepository;
import fr.diginamic.echolink.domain.airquality.AirQuality;
import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service implementation for air quality retrieval use cases.
 * Provides access to air quality data associated with locations.
 */
@Service
@RequiredArgsConstructor
public class AirQualityService implements AirQualityGetUseCase {

    /**
     * Maximum number of air quality records returned for a location.
     */
    private static final int LIMIT_AIR_QUALITY = 50;

    /**
     * Repository used to access air quality data.
     */
    private final AirQualityRepository repository;

    @Override
    public AirQuality getByLocationId(UUID locationId) throws LocationNotFoundException {
        return repository.getByLocationId(locationId)
                .orElseThrow(() -> new LocationNotFoundException("Location with id " + locationId + " not found"));
    }

    @Override
    public List<AirQuality> getAllByLocationId(UUID locationId) {
        return repository.getAllByLocationId(locationId, LIMIT_AIR_QUALITY);
    }
}
