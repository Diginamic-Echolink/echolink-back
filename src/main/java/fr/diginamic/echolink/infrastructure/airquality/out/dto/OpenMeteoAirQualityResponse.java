package fr.diginamic.echolink.infrastructure.airquality.out.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents the response returned by the Open-Meteo Air Quality API.
 *
 * @param hourly hourly air quality measurements
 */
public record OpenMeteoAirQualityResponse(Hourly hourly) {

    /**
     * Represents hourly air quality measurements returned by the Open-Meteo API.
     *
     * @param time timestamps associated with the measurements
     * @param pm10 PM10 particle concentrations
     * @param pm25 PM2.5 particle concentrations
     * @param euAqi European Air Quality Index values
     * @param carbonMonoxide carbon monoxide concentrations
     * @param ozone ozone concentrations
     * @param dust dust concentrations
     * @param nitrogenDioxide nitrogen dioxide concentrations
     * @param sulfurDioxide sulfur dioxide concentrations
     */
    public record Hourly(
            List<String> time,
            @JsonProperty("pm10") List<Float> pm10,
            @JsonProperty("pm2_5") List<Float> pm25,
            @JsonProperty("european_aqi") List<Byte> euAqi,
            @JsonProperty("carbon_monoxide") List<Float> carbonMonoxide,
            @JsonProperty("ozone") List<Float> ozone,
            @JsonProperty("dust") List<Float> dust,
            @JsonProperty("nitrogen_dioxide") List<Float> nitrogenDioxide,
            @JsonProperty("sulphur_dioxide") List<Float> sulfurDioxide
    ) {}
}
