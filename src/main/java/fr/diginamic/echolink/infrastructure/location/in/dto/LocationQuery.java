package fr.diginamic.echolink.infrastructure.location.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents location information returned to clients.
 *
 * @param id location UUID
 * @param name location name
 * @param postalCode postal code of the location
 * @param latitude geographic latitude
 * @param longitude geographic longitude
 * @param population population of the location
 */
@Schema(description = "Location information returned by the API")
public record LocationQuery (

        @Schema(description = "Unique identifier of the location", example = "550e8400-e29b-41d4-a716-446655440000")
        String id,

        @Schema(description = "Location name", example = "Saint-Étienne")
        String name,

        @Schema(description = "Postal code of the location", example = "42000")
        String postalCode,

        @Schema(description = "Latitude coordinate", example = "45.4397")
        double latitude,

        @Schema(description = "Longitude coordinate", example = "4.3872")
        double longitude,

        @Schema(description = "Population of the location", example = "173821")
        long population
) {
}
