package fr.diginamic.echolink.infrastructure.meteo.in.dto;

import fr.diginamic.echolink.domain.meteo.WeatherCondition;
import fr.diginamic.echolink.domain.meteo.WindDirection;

import java.time.LocalDateTime;

public record MeteoQuery(
        LocalDateTime recordedAt,
        WeatherCondition weatherCondition,
        float temperature,
        float atmPressure,
        float humidity,
        float windSpeed,
        WindDirection windDirection,
        float rainFall) {
}
