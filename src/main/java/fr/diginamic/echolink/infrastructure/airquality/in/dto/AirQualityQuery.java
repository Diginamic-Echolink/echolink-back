package fr.diginamic.echolink.infrastructure.airquality.in.dto;

import java.time.LocalDateTime;

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
