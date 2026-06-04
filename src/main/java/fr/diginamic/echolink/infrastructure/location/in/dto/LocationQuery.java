package fr.diginamic.echolink.infrastructure.location.in.dto;

public record LocationQuery (
    String name,
    String postalCode,
    double longitude,
    double latitude,
    long population) {
}
