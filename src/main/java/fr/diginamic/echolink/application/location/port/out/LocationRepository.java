package fr.diginamic.echolink.application.location.port.out;

import fr.diginamic.echolink.domain.location.Location;

import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.UUID;

public interface LocationRepository {

    List<Location> getAllLocations();

    Set<String> findAllInseeCodes();

    void saveAll(List<Location> locations);

    Optional<Location> getById(UUID id);

    List<Location> getByGeo(float latitudeMin, float latitudeMax, float longitudeMin, float longitudeMax, int limit);
}
