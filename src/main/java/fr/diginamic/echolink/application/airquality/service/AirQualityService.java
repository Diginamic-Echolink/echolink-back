package fr.diginamic.echolink.application.airquality.service;

import fr.diginamic.echolink.application.airquality.port.in.AirQualityGetUseCase;
import fr.diginamic.echolink.application.airquality.port.out.AirQualityRepository;
import fr.diginamic.echolink.application.location.port.in.LocationGetUseCase;
import fr.diginamic.echolink.domain.airquality.AirQuality;
import fr.diginamic.echolink.domain.airquality.exception.AirQualityNotFoundException;
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
     * Use case used to retrieve locations.
     */
    private final LocationGetUseCase locationGetUseCase;

    /**
     * Repository used to access air quality data.
     */
    private final AirQualityRepository repository;

    @Override
    public AirQuality getLastByLocationId(UUID locationId)
            throws LocationNotFoundException, AirQualityNotFoundException {

        validateLocationExists(locationId);

        return repository.getLastByLocationId(locationId)
                .orElseThrow(() -> new AirQualityNotFoundException(
                        "No air quality data found for location with id " + locationId
                ));
    }

    @Override
    public List<AirQuality> getAllByLocationId(UUID locationId)
            throws LocationNotFoundException, AirQualityNotFoundException {

        validateLocationExists(locationId);

        List<AirQuality> airQualities = repository.getAllByLocationId(locationId, LIMIT_AIR_QUALITY);

        if (airQualities.isEmpty()) {
            throw new AirQualityNotFoundException(
                    "No air quality data found for location with id " + locationId
            );
        }

        return airQualities;
    }

    private void validateLocationExists(UUID locationId) throws LocationNotFoundException {

        if (!locationGetUseCase.existsById(locationId)) {
            throw new LocationNotFoundException(
                    "Location with id " + locationId + " doesn't exist"
            );
        }
    }
}
