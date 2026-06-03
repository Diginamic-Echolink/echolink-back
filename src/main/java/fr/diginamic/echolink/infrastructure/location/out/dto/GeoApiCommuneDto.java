package fr.diginamic.echolink.infrastructure.location.out.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GeoApiCommuneDto(
        String nom,
        String code,
        @JsonProperty("codesPostaux") List<String> postalCodes,
        GeoApiLocationCentreDto centre,
        @JsonProperty("population") Long population) {
}
