package fr.diginamic.echolink.application.location.port.out;

import fr.diginamic.echolink.domain.location.Location;

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

    void saveAll(List<Location> locations);
}
