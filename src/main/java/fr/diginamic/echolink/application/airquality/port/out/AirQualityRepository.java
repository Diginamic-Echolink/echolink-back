package fr.diginamic.echolink.application.airquality.port.out;

import fr.diginamic.echolink.domain.airquality.AirQuality;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Output port for accessing and persisting air quality data.
 * Defines the operations required by the application layer
 * to retrieve and store air quality records.
 */
public interface AirQualityRepository {

    /**
     * Retrieves the latest air quality data associated with a location.
     *
     * @param locationId unique identifier of the location
     * @return an optional containing the air quality data if found
     */
    Optional<AirQuality> getLastByLocationId(UUID locationId);

    /**
     * Retrieves a limited number of air quality records for a location.
     *
     * @param locationId unique identifier of the location
     * @param LIMIT_AIR_QUALITY maximum number of records to retrieve
     * @return a list of air quality records associated with the location
     */
    List<AirQuality> getAllByLocationId(UUID locationId, int LIMIT_AIR_QUALITY);

    /**
     * Saves a collection of air quality records.
     *
     * @param airQualities list of air quality records to save
     */
    void saveAll(List<AirQuality> airQualities);
}
