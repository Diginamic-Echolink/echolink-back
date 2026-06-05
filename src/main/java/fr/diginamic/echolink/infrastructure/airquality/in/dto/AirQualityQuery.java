package fr.diginamic.echolink.infrastructure.airquality.in.dto;

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
public record AirQualityQuery(
        LocalDateTime recordedAt,
        float particles10,
        float particle25,
        byte euAqi,
        float carbonMonoxide,
        float ozone,
        float dust,
        float nitrogenDioxide,
        float sulfurDioxide) {
}
