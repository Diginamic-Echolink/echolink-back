package fr.diginamic.echolink.infrastructure.airquality.out.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OpenMeteoAirQualityResponse(Hourly hourly) {

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
