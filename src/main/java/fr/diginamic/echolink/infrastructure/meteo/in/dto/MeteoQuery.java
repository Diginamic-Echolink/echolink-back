package fr.diginamic.echolink.infrastructure.meteo.in.dto;

import fr.diginamic.echolink.domain.meteo.WeatherCondition;
import fr.diginamic.echolink.domain.meteo.WindDirection;
import io.swagger.v3.oas.annotations.media.Schema;

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

        @Schema(
                description = "Date and time when the weather data was recorded",
                example = "2026-06-09T12:30:00",
                type = "string",
                format = "date-time"
        )
        LocalDateTime recordedAt,

        @Schema(description = "Observed weather condition", example = "SUNNY")
        WeatherCondition weatherCondition,

        @Schema(description = "Measured temperature in degrees Celsius", example = "21.5")
        float temperature,

        @Schema(description = "Atmospheric pressure in hPa", example = "1013.2")
        float atmPressure,

        @Schema(description = "Humidity percentage", example = "65.0")
        float humidity,

        @Schema(description = "Wind speed in m/s", example = "5.4")
        float windSpeed,

        @Schema(description = "Wind direction", example = "NORTH_WEST")
        WindDirection windDirection,

        @Schema(description = "Rainfall amount in mm", example = "2.3")
        float rainFall
) {
}
