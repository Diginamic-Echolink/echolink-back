package fr.diginamic.echolink.application.airquality.port.in;

import fr.diginamic.echolink.domain.airquality.AirQuality;
import fr.diginamic.echolink.domain.airquality.exception.AirQualityNotFoundException;
import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Use case for retrieving air quality data associated with locations.
 */
public interface AirQualityGetUseCase {

    /**
     * Retrieves the latest air quality data for a given location.
     *
     * @param locationId unique identifier of the location
     * @return the latest air quality data associated with the location
     * @throws LocationNotFoundException if the location does not exist
     * @throws AirQualityNotFoundException if no air quality data exists for the location
     */
    AirQuality getLastByLocationId(UUID locationId) throws LocationNotFoundException, AirQualityNotFoundException;

    /**
     * Retrieves up to the configured maximum number of air quality records for a given location.
     *
     * @param locationId unique identifier of the location
     * @return a list of air quality records associated with the location
     * @throws LocationNotFoundException if the location does not exist
     * @throws AirQualityNotFoundException if no air quality data exists for the location
     */
    List<AirQuality> getAllByLocationId(UUID locationId) throws LocationNotFoundException, AirQualityNotFoundException;
}
