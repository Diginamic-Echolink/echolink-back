package fr.diginamic.echolink.application.location.port.out;

import fr.diginamic.echolink.domain.location.Location;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.UUID;

public interface LocationRepository {

    Optional<Location> getById(UUID id);

    List<Location> getByGeo(
            double latitudeMin,
            double latitudeMax,
            double longitudeMin,
            double longitudeMax,
            int limit
    );

    List<Location> getAllLocations();

    Set<String> findAllInseeCodes();

    List<Location> findLocationToSyncMeteoToday(LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<Location> findLocationToSyncAirQualityToday(LocalDateTime startOfDay, LocalDateTime endOfDay);

    void saveAll(List<Location> locations);
}
