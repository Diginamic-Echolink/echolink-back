package fr.diginamic.echolink.application.location.port.in;

import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Defines the use cases for retrieving locations.
 */
public interface LocationGetUseCase {

    /**
     * Retrieves a location by its unique identifier.
     *
     * @param id unique identifier of the location
     * @return the matching location
     * @throws LocationNotFoundException if no location is found with the specified identifier
     */
    Location getById(UUID id) throws LocationNotFoundException;

    /**
     * Retrieves all locations whose name contains the specified search term.
     *
     * @param search text used to search location names
     * @return list of matching locations
     */
    List<Location> getAllByNameContaining(String search);

    /**
     * Retrieves all available locations.
     *
     * @return list of all locations
     */
    List<Location> getAllLocations();

    /**
     * Retrieves locations located within a geographic area around the specified coordinates.
     *
     * @param latitude reference latitude
     * @param longitude reference longitude
     * @param delta search radius or geographic range
     * @return list of matching locations
     */
    List<Location> getAllByGeolocalizationBetween(double latitude, double longitude, int delta);
}
