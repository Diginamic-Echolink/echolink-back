package fr.diginamic.echolink.infrastructure.meteo.out.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OpenMeteoResponse(Daily daily, Hourly hourly) {

    public record Daily(
            List<String> time,
            @JsonProperty("weather_code") List<Integer> weatherCode,
            @JsonProperty("temperature_2m_max") List<Float> tempMax,
            @JsonProperty("temperature_2m_min") List<Float> tempMin,
            @JsonProperty("precipitation_sum") List<Float> precipitationSum
    ) {}

    public record Hourly(
            List<String> time,
            @JsonProperty("relative_humidity_2m") List<Float> humidity,
            @JsonProperty("pressure_msl") List<Float> pressure,
            @JsonProperty("wind_speed_10m") List<Float> windSpeed,
            @JsonProperty("wind_direction_10m") List<Integer> windDirection
    ) {}
}
