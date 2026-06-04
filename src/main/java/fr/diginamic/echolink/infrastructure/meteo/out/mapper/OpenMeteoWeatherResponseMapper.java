package fr.diginamic.echolink.infrastructure.meteo.out.mapper;

import fr.diginamic.echolink.domain.meteo.Meteo;
import fr.diginamic.echolink.domain.meteo.WeatherCondition;
import fr.diginamic.echolink.domain.meteo.WindDirection;
import fr.diginamic.echolink.infrastructure.meteo.out.dto.OpenMeteoWeatherResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OpenMeteoWeatherResponseMapper {

    private static final int DAILY_INDEX = 0;
    private static final int NOON_INDEX = 12;

    public Meteo toMeteo(OpenMeteoWeatherResponse dto) {

        OpenMeteoWeatherResponse.Daily dailyDatas = dto.daily();
        OpenMeteoWeatherResponse.Hourly hourlyDatas = dto.hourly();

        return new Meteo(
                LocalDateTime.now(),
                WeatherCondition.fromWmoCode(dailyDatas.weatherCode().get(DAILY_INDEX)),
                average(dailyDatas.tempMin().get(DAILY_INDEX), dailyDatas.tempMax().get(DAILY_INDEX)),
                hourlyDatas.pressure().get(NOON_INDEX),
                hourlyDatas.humidity().get(NOON_INDEX),
                hourlyDatas.windSpeed().get(NOON_INDEX),
                WindDirection.fromDegrees(hourlyDatas.windDirection().get(NOON_INDEX)),
                dailyDatas.precipitationSum().get(DAILY_INDEX)
        );
    }

    private float average(float min, float max) {
        return (min + max) / 2f;
    }
}
