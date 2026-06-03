package fr.diginamic.echolink.infrastructure.location.in.dto;

public record LocationQuery (
    String name,
    String postalCode,
    float longitude,
    float latitude) {
}
