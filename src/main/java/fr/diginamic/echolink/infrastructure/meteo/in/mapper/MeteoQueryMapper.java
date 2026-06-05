package fr.diginamic.echolink.infrastructure.meteo.in.mapper;

import fr.diginamic.echolink.domain.meteo.Meteo;
import fr.diginamic.echolink.infrastructure.meteo.in.dto.MeteoQuery;
import org.springframework.stereotype.Component;

/**
 * Maps weather domain objects to weather query DTOs.
 */
@Component
public class MeteoQueryMapper {

    /**
     * Converts a weather domain object into a weather query DTO.
     *
     * @param meteo weather domain object to convert
     * @return corresponding weather query DTO
     */
    public MeteoQuery toQuery(Meteo meteo) {

        return new MeteoQuery(
                meteo.getRecordedAt(),
                meteo.getWeatherCondition(),
                meteo.getTemperature(),
                meteo.getAtmPressure(),
                meteo.getHumidity(),
                meteo.getWindSpeed(),
                meteo.getWindDirection(),
                meteo.getRainFall()
        );
    }
}
