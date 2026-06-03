package fr.diginamic.echolink.application.location.service;

import fr.diginamic.echolink.application.location.port.in.LocationGetUseCase;
import fr.diginamic.echolink.application.location.port.out.LocationRepository;
import fr.diginamic.echolink.domain.location.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationGetService implements LocationGetUseCase {

    private final int limite = 10;
    private final double degreradToKm = 111.11;
    private int delta;
    //private float latitudeMin;


    private final LocationRepository repository;

    @Override
    public Location getById(UUID id) {
        return repository.getById(id).orElse(null);
    }

    @Override
    public List<Location> getByGeo(float latitude, float longitude, int delta) {

        double latitudeMin = latitudeCalculeMin(latitude, delta);
        double latitudeMax = latitudeCalculeMax(latitude, delta);

        double longitudeMin = longitudeCalculeMin(delta, latitude, longitude);
        double longitudeMax = longitudeCalculeMax(delta, latitude, longitude);

        return repository.getByGeo((float)latitudeMin, (float)latitudeMax , (float)longitudeMin, (float)longitudeMax, limite);
    }

    private double latitudeCalculeMin(float latitude, int delta) {

        double deltaDegre = deltaLatitude(delta);

        double latitudeMin = latitude - deltaDegre;
        return latitudeMin;
    }

    private double latitudeCalculeMax(float latitude, int delta) {

        double deltaDegre = deltaLatitude(delta);

        double latitudeMAx = latitude + deltaDegre;
        return latitudeMAx;
    }

    private double longitudeCalculeMin(int delta, float latitude, float longitude) {

        double deltaDegre = deltaLongitude(delta, latitude);

        double longitudeMin = longitude - deltaDegre;
        return longitudeMin;
    }

    private double longitudeCalculeMax(int delta, float latitude, float longitude) {

        double deltaDegre = deltaLongitude(delta, latitude);

        double longitudeMax = longitude + deltaDegre;
        return longitudeMax;
    }

    private double deltaLatitude(int delta) {

        // 1° ~= 111km donc on prend le delta en km que l'on divise par la distance
        return (double)delta / degreradToKm;
    }

    private double deltaLongitude(int delta, float latitude) {

        // 1° ~= 111km * cos(latitude°) Le cof change en fonction du placement de la latitude

        // Conversion en radiant
        double latitudeRad = Math.toRadians(latitude);

        // Calcul de la distance en km
        double coefLatitudeKm = Math.cos(latitudeRad);
        return (double)delta / (degreradToKm * coefLatitudeKm);
    }
}
