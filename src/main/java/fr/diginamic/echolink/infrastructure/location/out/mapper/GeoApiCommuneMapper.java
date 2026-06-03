package fr.diginamic.echolink.infrastructure.location.out.mapper;

import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.infrastructure.location.out.dto.GeoApiCommuneDto;
import org.springframework.stereotype.Component;

@Component
public class GeoApiCommuneMapper {

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
