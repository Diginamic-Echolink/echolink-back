package fr.diginamic.echolink.infrastructure.airquality.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Data transfer object used to expose air quality information through the API.
 *
 * @param recordedAt date and time when the air quality data was recorded
 * @param particles10 concentration of PM10 particles
 * @param particle25 concentration of PM2.5 particles
 * @param euAqi European Air Quality Index value
 * @param carbonMonoxide concentration of carbon monoxide
 * @param ozone concentration of ozone
 * @param dust concentration of dust particles
 * @param nitrogenDioxide concentration of nitrogen dioxide
 * @param sulfurDioxide concentration of sulfur dioxide
 */
@Schema(description = "Air quality measurement data returned by the API")
public record AirQualityQuery(

        @Schema(
                description = "Timestamp of measurement",
                example = "2026-06-09T12:30:00",
                type = "string",
                format = "date-time"
        )
        LocalDateTime recordedAt,

        @Schema(description = "Concentration of PM10 particles (µg/m³)", example = "12.5")
        float particles10,

        @Schema(description = "Concentration of PM2.5 particles (µg/m³)", example = "8.3")
        float particle25,

        @Schema(
                description = "European Air Quality Index value (0-100)",
                example = "42",
                minimum = "0",
                maximum = "100"
        )
        byte euAqi,

        @Schema(description = "Carbon monoxide concentration (µg/m³)", example = "0.4")
        float carbonMonoxide,

        @Schema(description = "Ozone concentration (µg/m³)", example = "65.2")
        float ozone,

        @Schema(description = "Dust particle concentration (µg/m³)", example = "15.0")
        float dust,

        @Schema(description = "Nitrogen dioxide concentration (µg/m³)", example = "22.1")
        float nitrogenDioxide,

        @Schema(description = "Sulfur dioxide concentration (µg/m³)", example = "5.8")
        float sulfurDioxide
) {
}
