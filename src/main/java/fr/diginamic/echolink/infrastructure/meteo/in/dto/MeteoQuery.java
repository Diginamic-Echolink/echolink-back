package fr.diginamic.echolink.infrastructure.meteo.in.dto;

import fr.diginamic.echolink.domain.meteo.WindDirection;

import java.time.LocalDateTime;

public record MeteoQuery(
        LocalDateTime recordedAt,
        byte temperature,
        int atmPressure,
        byte windSpeed,
        WindDirection windDirection,
        int rainFall,
        byte humidity) {
}

