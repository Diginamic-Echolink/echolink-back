package fr.diginamic.echolink.infrastructure.location.out.persistence;

import fr.diginamic.echolink.application.location.port.out.LocationRepository;
import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.infrastructure.location.out.persistence.repository.LocationJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.UUID;

/**
 * Adapter implementing the {@link LocationRepository} port.
 * <p>
 * Delegates persistence operations to the underlying
 * {@link LocationJdbcRepository}.
 */
@Component
@RequiredArgsConstructor
public class LocationRepositoryAdapter implements LocationRepository {

    private final LocationJdbcRepository repository;

    /**
     * Retrieves a location by its identifier.
     *
     * @param id location identifier
     * @return an optional containing the location if found
     */
    @Override
    public Optional<Location> getById(UUID id) {
        return repository.findById(id);
    }

    /**
     * Retrieves locations whose name contains the specified value.
     *
     * @param name location name or partial name
     * @return matching locations
     */
    @Override
    public List<Location> getAllByNameContaining(String name) {
        return repository.findAllByNameContaining(name);
    }

    /**
     * Retrieves locations located within the specified geographical boundaries.
     *
     * @param latitudeMin minimum latitude
     * @param latitudeMax maximum latitude
     * @param longitudeMin minimum longitude
     * @param longitudeMax maximum longitude
     * @param limit maximum number of results
     * @return matching locations
     */
    @Override
    public List<Location> getByGeolocalizationBetween(
            double latitudeMin,
            double latitudeMax,
            double longitudeMin,
            double longitudeMax,
            int limit
    ) {
        return repository.findAllByCordonneeBetween(latitudeMin, latitudeMax, longitudeMin, longitudeMax, limit);
    }

    /**
     * Retrieves all stored locations.
     *
     * @return list of locations
     */
    @Override
    public List<Location> getAllLocations() {
        return repository.findAll();
    }

    /**
     * Retrieves all stored INSEE codes.
     *
     * @return set of INSEE codes
     */
    @Override
    public Set<String> getAllInseeCodes() {
        return repository.findAllInseeCodes();
    }

    /**
     * Retrieves locations that do not have weather data
     * synchronized for the specified day.
     *
     * @param startOfDay start of the day
     * @param endOfDay end of the day
     * @return locations requiring weather synchronization
     */
    @Override
    public List<Location> getAllLocationsToSyncMeteoToday(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return repository.findAllLocationsToSyncMeteoToday(startOfDay, endOfDay);
    }

    /**
     * Retrieves locations that do not have air quality data
     * synchronized for the specified day.
     *
     * @param startOfDay start of the day
     * @param endOfDay end of the day
     * @return locations requiring air quality synchronization
     */
    @Override
    public List<Location> getAllLocationsToSyncAirQualityToday(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return repository.findAllLocationsToSyncAirQualityToday(startOfDay, endOfDay);
    }

    /**
     * Saves a collection of locations.
     *
     * @param locations locations to save
     */
    @Override
    public void saveAll(List<Location> locations) {
        repository.saveAll(locations);
    }
}
