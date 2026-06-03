package fr.diginamic.echolink.infrastructure.airquality.in.mapper;

import fr.diginamic.echolink.domain.airquality.AirQuality;
import fr.diginamic.echolink.infrastructure.airquality.in.dto.AirQualityQuery;
import org.springframework.stereotype.Component;

@Component
public class AirQualityQueryMapper {

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
