package fr.diginamic.echolink.infrastructure.meteo.in.mapper;

import fr.diginamic.echolink.domain.meteo.Meteo;
import fr.diginamic.echolink.infrastructure.meteo.in.dto.MeteoQuery;
import org.springframework.stereotype.Component;

@Component
public class MeteoQueryMapper {

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

