package fr.diginamic.echolink.infrastructure.location.in.mapper;

import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.infrastructure.location.in.dto.LocationQuery;
import org.springframework.stereotype.Component;

@Component
public class LocationQueryMapper {

    public LocationQuery toQuery(Location location) {
        return new LocationQuery(
                location.getName(),
                location.getPostalCode(),
                location.getLongitude(),
                location.getLatitude(),
                location.getAltitude()
        );
    }
}
