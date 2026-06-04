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

    public Optional<Location> getById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Location> getByGeo(
            double latitudeMin,
            double latitudeMax,
            double longitudeMin,
            double longitudeMax,
            int limit
    ) {
        return repository.findLocationsByCordonneeBetween(latitudeMin, latitudeMax, longitudeMin, longitudeMax, limit);
    }

    @Override
    public Optional<Location> getById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Location> getAllByNameContaining(String name) {
        return repository.findAllByNameContaining(name);
    }

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

    @Override
    public List<Location> getAllLocations() {
        return repository.findAll();
    }

    @Override
    public Set<String> getAllInseeCodes() {
        return repository.findAllInseeCodes();
    }

    @Override
    public List<Location> getAllLocationsToSyncMeteoToday(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return repository.findAllLocationsToSyncMeteoToday(startOfDay, endOfDay);
    }

    @Override
    public List<Location> getAllLocationsToSyncAirQualityToday(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return repository.findAllLocationsToSyncAirQualityToday(startOfDay, endOfDay);
    }

    @Override
    public void saveAll(List<Location> locations) {
        repository.saveAll(locations);
    }
}
