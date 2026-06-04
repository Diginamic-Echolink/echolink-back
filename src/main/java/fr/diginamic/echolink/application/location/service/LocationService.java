package fr.diginamic.echolink.application.location.service;

import fr.diginamic.echolink.application.location.port.in.LocationGetUseCase;
import fr.diginamic.echolink.application.location.port.out.LocationRepository;
import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationService implements LocationGetUseCase {

    private static final int LIMIT_LOCATION = 10;

    /** Constant for the conversion of 1° to km */
    private static final double DEGRE_TO_KM_CONVERSION = 111.11;

    private final LocationRepository repository;

    @Override
    public Location getById(UUID id) throws LocationNotFoundException {
        return repository.getById(id)
                .orElseThrow(() -> new LocationNotFoundException("Location with id " + id + " not found"));
    }

    @Override
    public List<Location> getByGeo(double latitude, double longitude, int delta) {

        double deltaDegreLatitude = delta / DEGRE_TO_KM_CONVERSION;
        double deltaDegreLongitude = deltaLongitudeToKm(latitude, delta);

        double latitudeMin = latitude - deltaDegreLatitude;
        double latitudeMax = latitude + deltaDegreLatitude;

        double longitudeMin = longitude - deltaDegreLongitude;
        double longitudeMax = longitude + deltaDegreLongitude;

        return repository.getByGeo(latitudeMin, latitudeMax , longitudeMin, longitudeMax, LIMIT_LOCATION);
    }

    private double deltaLongitudeToKm(double latitude, int delta) {

        // 1° ~= 111km * cos(latitude°)
        double latitudeRad = Math.toRadians(latitude);

        // Calculating the distance in km
        double latitudeCoefficient = Math.cos(latitudeRad);
        return delta / (DEGRE_TO_KM_CONVERSION * latitudeCoefficient);
    }
}
