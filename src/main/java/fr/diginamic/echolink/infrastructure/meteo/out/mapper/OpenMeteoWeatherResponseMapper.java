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

@Component
public class OpenMeteoWeatherResponseMapper {

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
