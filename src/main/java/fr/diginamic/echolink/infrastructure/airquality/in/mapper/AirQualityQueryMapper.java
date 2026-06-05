package fr.diginamic.echolink.infrastructure.airquality.in.mapper;

import fr.diginamic.echolink.domain.airquality.AirQuality;
import fr.diginamic.echolink.infrastructure.airquality.in.dto.AirQualityQuery;
import org.springframework.stereotype.Component;

/**
 * Mapper responsible for converting {@link AirQuality} domain objects
 * into {@link AirQualityQuery} DTOs.
 */
@Component
public class AirQualityQueryMapper {

    /**
     * Converts an air quality domain object into a query DTO.
     *
     * @param airQuality air quality domain object to convert
     * @return the corresponding air quality query DTO
     */
    public AirQualityQuery toQuery(AirQuality airQuality) {
        return new AirQualityQuery(
                airQuality.getRecordedAt(),
                airQuality.getParticles10(),
                airQuality.getParticles25(),
                airQuality.getEuAqi(),
                airQuality.getCarbonMonoxide(),
                airQuality.getOzone(),
                airQuality.getDust(),
                airQuality.getNitrogenDioxide(),
                airQuality.getSulfurDioxide()
        );
    }
}
