package fr.diginamic.echolink.application.location.service;

import fr.diginamic.echolink.application.location.port.in.LocationGetUseCase;
import fr.diginamic.echolink.application.location.port.out.LocationRepository;
import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public boolean existsById(UUID id) {
       return repository.existsById(id);
    }

    @Override
    public Location getById(UUID id) throws LocationNotFoundException {
        return repository.getById(id)
                .orElseThrow(() -> new LocationNotFoundException("Location with id " + id + " not found"));
    }

    @Override
    public List<Location> getAllByNameContaining(String search) {
        return repository.getAllByNameContaining(search);
    }

    @Override
    public List<Location> getAllLocations() {
        return repository.getAllLocations();
    }

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

    @Override
    public List<Location> getAllLocationsToSyncMeteoToday(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return repository.getAllLocationsToSyncMeteoToday(startOfDay, endOfDay);
    }

    @Override
    public List<Location> getAllLocationsToSyncAirQualityToday(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return repository.getAllLocationsToSyncAirQualityToday(startOfDay, endOfDay);
    }
}
