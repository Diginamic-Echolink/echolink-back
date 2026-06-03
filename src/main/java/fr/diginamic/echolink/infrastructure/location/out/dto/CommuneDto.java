package fr.diginamic.echolink.infrastructure.location.out.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CommuneDto(
        String nom,
        String code,
        @JsonProperty("codesPostaux")
        List<String> postalCodes,
        LocationCentreDto centre) {
}
