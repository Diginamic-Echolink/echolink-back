package fr.diginamic.echolink.infrastructure.location.out.mapper;

import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.infrastructure.location.out.dto.GeoApiCommuneDto;
import org.springframework.stereotype.Component;

/**
 * Mapper responsible for converting Geo API commune data
 * into {@link Location} domain objects.
 */
@Component
public class GeoApiCommuneMapper {

    /**
     * Converts a Geo API commune DTO into a {@link Location} entity.
     * <p>
     * If geographical coordinates, postal codes, or population
     * are not provided by the API, default values are used.
     *
     * @param dto commune data returned by the Geo API
     * @return the corresponding {@link Location} entity
     */
    public Location toLocation(GeoApiCommuneDto dto) {

        double longitude = dto.centre() != null
                ? dto.centre().coordinates().get(0)
                : 0;

        double latitude = dto.centre() != null
                ? dto.centre().coordinates().get(1)
                : 0;

        String postalCode = dto.postalCodes() != null && !dto.postalCodes().isEmpty()
                ? dto.postalCodes().getFirst()
                : null;

        long population = dto.population() != null ? dto.population() : 0;

        return new Location(dto.nom(), dto.code(), postalCode, longitude, latitude, population);
    }
}
