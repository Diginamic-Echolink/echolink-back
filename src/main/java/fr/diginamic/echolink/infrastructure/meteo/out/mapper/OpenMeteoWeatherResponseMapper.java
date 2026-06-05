package fr.diginamic.echolink.infrastructure.meteo.out.mapper;

import fr.diginamic.echolink.domain.meteo.Meteo;
import fr.diginamic.echolink.domain.meteo.WeatherCondition;
import fr.diginamic.echolink.domain.meteo.WindDirection;
import fr.diginamic.echolink.infrastructure.meteo.out.dto.OpenMeteoWeatherResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static fr.diginamic.echolink.domain.shared.utils.CalcUtils.averageFloat;
import static fr.diginamic.echolink.domain.shared.utils.CalcUtils.averageInteger;

/**
 * Maps Open-Meteo API responses to weather domain objects.
 */
@Component
public class OpenMeteoWeatherResponseMapper {

    /**
     * Converts an Open-Meteo response into a weather domain object.
     *
     * @param dto Open-Meteo response to convert
     * @return corresponding weather domain object
     */
    public Meteo toMeteo(OpenMeteoWeatherResponse dto) {

        OpenMeteoWeatherResponse.Daily daily = dto.daily();
        OpenMeteoWeatherResponse.Hourly hourly = dto.hourly();

        return new Meteo(
                LocalDateTime.now(),
                WeatherCondition.fromWmoCode(daily.weatherCode().getFirst()),
                averageFloat(List.of(daily.tempMin().getFirst(), daily.tempMax().getFirst())),
                averageFloat(hourly.pressure()),
                averageFloat(hourly.humidity()),
                averageFloat(hourly.windSpeed()),
                WindDirection.fromDegrees(averageInteger(hourly.windDirection())),
                daily.precipitationSum().getFirst()
        );
    }
}
