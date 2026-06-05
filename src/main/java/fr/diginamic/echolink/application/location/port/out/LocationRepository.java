package fr.diginamic.echolink.application.location.port.out;

import fr.diginamic.echolink.domain.location.Location;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.UUID;

public interface LocationRepository {

    Optional<Location> getById(UUID id);

    List<Location> getAllByNameContaining(String name);

    List<Location> getByGeolocalizationBetween(
            double latitudeMin,
            double latitudeMax,
            double longitudeMin,
            double longitudeMax,
            int limit
    );

    List<Location> getAllLocations();

    Set<String> getAllInseeCodes();

    List<Location> getAllLocationsToSyncMeteoToday(LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<Location> getAllLocationsToSyncAirQualityToday(LocalDateTime startOfDay, LocalDateTime endOfDay);

    void saveAll(List<Location> locations);
}
