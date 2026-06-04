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
    public List<Location> getAllLocations() {
        return repository.findAll();
    }

    @Override
    public Set<String> findAllInseeCodes() {
        return repository.findAllInseeCodes();
    }

    @Override
    public List<Location> findLocationToSyncMeteoToday(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return repository.findLocationToSyncMeteoToday(startOfDay, endOfDay);
    }

    @Override
    public List<Location> findLocationToSyncAirQualityToday(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return repository.findLocationToSyncAirQualityToday(startOfDay, endOfDay);
    }

    @Override
    public void saveAll(List<Location> locations) {
        repository.saveAll(locations);
    }
}
