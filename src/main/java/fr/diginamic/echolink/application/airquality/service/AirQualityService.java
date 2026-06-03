package fr.diginamic.echolink.application.airquality.service;

import fr.diginamic.echolink.application.airquality.port.in.AirQualityGetUseCase;
import fr.diginamic.echolink.application.airquality.port.out.AirQualityRepository;
import fr.diginamic.echolink.domain.airquality.AirQuality;
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

    /**
     * Retrieves the latest air quality data for a given location.
     *
     * @param locationId unique identifier of the location
     * @return the air quality data associated with the location,
     * or {@code null} if no data is available
     */
    @Override
    public AirQuality getByLocationId(UUID locationId) {
        return repository.getByLocationId(locationId).orElse(null);
    }

    /**
     * Retrieves a list of air quality records for a given location.
     *
     * @param locationId unique identifier of the location
     * @return a list of air quality records associated with the location
     */
    @Override
    public List<AirQuality> getAllByLocationId(UUID locationId) {
        return repository.getAllByLocationId(locationId, LIMIT_AIR_QUALITY);
    }
}
