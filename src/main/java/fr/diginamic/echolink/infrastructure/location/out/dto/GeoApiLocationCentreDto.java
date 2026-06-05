package fr.diginamic.echolink.infrastructure.location.out.dto;

import java.util.List;

/**
 * Data Transfer Object representing the geographical center
 * coordinates of a location returned by the Geo API.
 *
 * @param coordinates geographic coordinates of the location center
 *                    expressed as a list containing longitude and latitude
 */
public record GeoApiLocationCentreDto(List<Double> coordinates) {}