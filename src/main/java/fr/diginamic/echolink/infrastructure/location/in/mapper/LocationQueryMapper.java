package fr.diginamic.echolink.infrastructure.location.in.mapper;

import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.infrastructure.location.in.dto.LocationQuery;
import org.springframework.stereotype.Component;

/**
 * Maps location domain objects to location query DTOs.
 */
@Component
public class LocationQueryMapper {

    /**
     * Converts a location domain object into a location query DTO.
     *
     * @param location location domain object to convert
     * @return corresponding location query DTO
     */
    public LocationQuery toQuery(Location location) {
        
        return new LocationQuery(
                location.getName(),
                location.getPostalCode(),
                location.getLatitude(),
                location.getLongitude(),
                location.getPopulation()
        );
    }
}
