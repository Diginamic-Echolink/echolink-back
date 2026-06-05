package fr.diginamic.echolink.application.airquality.port.in;

import fr.diginamic.echolink.domain.airquality.AirQuality;
import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Use case for retrieving air quality data associated with locations.
 */
public interface AirQualityGetUseCase {

    /**
     * Retrieves the latest air quality data for a location.
     *
     * @param locationId unique identifier of the location
     * @return the air quality data associated with the location
     * @throws LocationNotFoundException if the location id is not found
     */
    AirQuality getByLocationId(UUID locationId) throws LocationNotFoundException;

    /**
     * Retrieves all available air quality records for a location.
     *
     * @param locationId unique identifier of the location
     * @return a list of air quality records associated with the location
     */
    List<AirQuality> getAllByLocationId(UUID locationId);
}
