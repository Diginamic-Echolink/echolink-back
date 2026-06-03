package fr.diginamic.echolink.infrastructure.location.out.mapper;

import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.infrastructure.location.out.dto.CommuneDto;
import org.springframework.stereotype.Component;

@Component
public class CommuneMapper {

    public Location toLocation(CommuneDto dto) {

        float longitude = dto.centre() != null
                ? dto.centre().coordinates().get(0)
                : 0;

        float latitude = dto.centre() != null
                ? dto.centre().coordinates().get(1)
                : 0;

        String postalCode = dto.postalCodes() != null && !dto.postalCodes().isEmpty()
                ? dto.postalCodes().getFirst()
                : null;

        return new Location(dto.nom(), dto.code(), postalCode, longitude, latitude);
    }
}
