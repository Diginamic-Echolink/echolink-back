package fr.diginamic.echolink.infrastructure.location.in.dto;

/**
 * Represents location information returned to clients.
 *
 * @param name location name
 * @param postalCode postal code of the location
 * @param latitude geographic latitude
 * @param longitude geographic longitude
 * @param population population of the location
 */
public record LocationQuery (
        String name,
        String postalCode,
        double latitude,
        double longitude,
        long population) {
}
