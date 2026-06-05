package fr.diginamic.echolink.infrastructure.meteo.in.dto;

import fr.diginamic.echolink.domain.meteo.WeatherCondition;
import fr.diginamic.echolink.domain.meteo.WindDirection;

import java.time.LocalDateTime;

/**
 * Data transfer object representing weather information returned to clients.
 *
 * @param recordedAt date and time when the weather data was recorded
 * @param weatherCondition observed weather condition
 * @param temperature measured temperature
 * @param atmPressure measured atmospheric pressure
 * @param humidity measured humidity level
 * @param windSpeed measured wind speed
 * @param windDirection measured wind direction
 * @param rainFall measured rainfall amount
 */
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
