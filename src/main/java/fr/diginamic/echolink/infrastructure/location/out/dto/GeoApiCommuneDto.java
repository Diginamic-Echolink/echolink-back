package fr.diginamic.echolink.infrastructure.location.out.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents a commune returned by the French Geo API.
 *
 * @param nom commune name
 * @param code INSEE code of the commune
 * @param postalCodes postal codes associated with the commune
 * @param centre geographic center of the commune
 * @param population population of the commune
 */
public record GeoApiCommuneDto(
        String nom,
        String code,
        @JsonProperty("codesPostaux") List<String> postalCodes,
        GeoApiLocationCentreDto centre,
        @JsonProperty("population") Long population) {
}
