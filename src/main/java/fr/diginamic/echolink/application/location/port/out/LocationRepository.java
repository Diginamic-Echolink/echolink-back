package fr.diginamic.echolink.application.location.port.out;

import fr.diginamic.echolink.domain.location.Location;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.UUID;

/**
 * Defines the contract for accessing and persisting location data.
 */
public interface LocationRepository {

    /**
     * Retrieves a location by its unique identifier.
     *
     * @param id unique identifier of the location
     * @return an {@link Optional} containing the location if found
     */
    Optional<Location> getById(UUID id);

    /**
     * Retrieves locations whose name contains the specified value.
     *
     * @param name text used to search location names
     * @return list of matching locations
     */
    List<Location> getAllByNameContaining(String name);

    /**
     * Retrieves locations within the specified geographic boundaries.
     *
     * @param latitudeMin minimum latitude
     * @param latitudeMax maximum latitude
     * @param longitudeMin minimum longitude
     * @param longitudeMax maximum longitude
     * @param limit maximum number of locations to retrieve
     * @return list of matching locations
     */
    List<Location> getByGeolocalizationBetween(
            double latitudeMin,
            double latitudeMax,
            double longitudeMin,
            double longitudeMax,
            int limit
    );

    /**
     * Retrieves all available locations.
     *
     * @return list of all locations
     */
    List<Location> getAllLocations();

    /**
     * Retrieves all location INSEE codes.
     *
     * @return set of INSEE codes
     */
    Set<String> getAllInseeCodes();

    /**
     * Retrieves locations that require weather synchronization for the current day.
     *
     * @param startOfDay start of the synchronization period
     * @param endOfDay end of the synchronization period
     * @return list of locations to synchronize
     */
    List<Location> getAllLocationsToSyncMeteoToday(LocalDateTime startOfDay, LocalDateTime endOfDay);

    /**
     * Retrieves locations that require air quality synchronization for the current day.
     *
     * @param startOfDay start of the synchronization period
     * @param endOfDay end of the synchronization period
     * @return list of locations to synchronize
     */
    List<Location> getAllLocationsToSyncAirQualityToday(LocalDateTime startOfDay, LocalDateTime endOfDay);

    /**
     * Persists a collection of locations.
     *
     * @param locations locations to save
     */
    void saveAll(List<Location> locations);
}
