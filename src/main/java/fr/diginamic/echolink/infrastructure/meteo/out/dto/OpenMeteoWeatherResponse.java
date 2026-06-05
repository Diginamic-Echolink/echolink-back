package fr.diginamic.echolink.infrastructure.meteo.out.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents the weather response returned by the Open-Meteo API.
 *
 * @param daily daily weather data
 * @param hourly hourly weather data
 */
public record OpenMeteoWeatherResponse(Daily daily, Hourly hourly) {

    /**
     * Represents daily weather measurements returned by the Open-Meteo API.
     *
     * @param time dates associated with the daily measurements
     * @param weatherCode weather condition codes
     * @param tempMax maximum daily temperatures
     * @param tempMin minimum daily temperatures
     * @param precipitationSum total daily precipitation
     */
    public record Daily(
            List<String> time,
            @JsonProperty("weather_code") List<Integer> weatherCode,
            @JsonProperty("temperature_2m_max") List<Float> tempMax,
            @JsonProperty("temperature_2m_min") List<Float> tempMin,
            @JsonProperty("precipitation_sum") List<Float> precipitationSum
    ) {}

    /**
     * Represents hourly weather measurements returned by the Open-Meteo API.
     *
     * @param time timestamps associated with the hourly measurements
     * @param humidity hourly humidity values
     * @param pressure hourly atmospheric pressure values
     * @param windSpeed hourly wind speed values
     * @param windDirection hourly wind direction values
     */
    public record Hourly(
            List<String> time,
            @JsonProperty("relative_humidity_2m") List<Float> humidity,
            @JsonProperty("pressure_msl") List<Float> pressure,
            @JsonProperty("wind_speed_10m") List<Float> windSpeed,
            @JsonProperty("wind_direction_10m") List<Integer> windDirection
    ) {}
}
