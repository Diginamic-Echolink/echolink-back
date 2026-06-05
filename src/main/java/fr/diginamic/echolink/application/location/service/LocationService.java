package fr.diginamic.echolink.application.location.service;

import fr.diginamic.echolink.application.location.port.in.LocationGetUseCase;
import fr.diginamic.echolink.application.location.port.out.LocationRepository;
import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static fr.diginamic.echolink.domain.shared.utils.CalcUtils.deltaLatitudeToKm;
import static fr.diginamic.echolink.domain.shared.utils.CalcUtils.deltaLongitudeToKm;

/**
 * Service responsible for location retrieval operations.
 */
@Service
@RequiredArgsConstructor
public class LocationService implements LocationGetUseCase {

    /**
     * Maximum number of locations returned by a geolocation search.
     */
    private static final int LIMIT_LOCATION = 10;

    /**
     * Repository used to access location data.
     */
    private final LocationRepository repository;

    /**
     * Retrieves a location by its unique identifier.
     *
     * @param id unique identifier of the location
     * @return the matching location
     * @throws LocationNotFoundException if no location is found with the specified identifier
     */
    @Override
    public Location getById(UUID id) throws LocationNotFoundException {
        return repository.getById(id)
                .orElseThrow(() -> new LocationNotFoundException("Location with id " + id + " not found"));
    }

    /**
     * Retrieves all locations whose name contains the specified search term.
     *
     * @param search text used to search location names
     * @return list of matching locations
     */
    @Override
    public List<Location> getAllByNameContaining(String search) {
        return repository.getAllByNameContaining(search);
    }

    /**
     * Retrieves all available locations.
     *
     * @return list of all locations
     */
    @Override
    public List<Location> getAllLocations() {
        return repository.getAllLocations();
    }

    /**
     * Retrieves locations located within a geographic area around the specified coordinates.
     *
     * @param latitude reference latitude
     * @param longitude reference longitude
     * @param delta search radius in kilometers
     * @return list of matching locations
     */
    @Override
    public List<Location> getAllByGeolocalizationBetween(double latitude, double longitude, int delta) {

        double deltaDegreLatitude = deltaLatitudeToKm(delta);
        double deltaDegreLongitude = deltaLongitudeToKm(latitude, delta);

        double latitudeMin = latitude - deltaDegreLatitude;
        double latitudeMax = latitude + deltaDegreLatitude;

        double longitudeMin = longitude - deltaDegreLongitude;
        double longitudeMax = longitude + deltaDegreLongitude;

        return repository.getByGeolocalizationBetween(latitudeMin, latitudeMax , longitudeMin, longitudeMax, LIMIT_LOCATION);
    }
}
