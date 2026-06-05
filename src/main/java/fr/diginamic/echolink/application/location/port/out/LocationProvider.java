package fr.diginamic.echolink.application.location.port.out;

import fr.diginamic.echolink.domain.location.Location;

import java.util.List;

/**
 * Defines the contract for retrieving location data from an external provider.
 */
public interface LocationProvider {

    /**
     * Retrieves all available locations from the external data source.
     *
     * @return list of available locations
     */
    List<Location> getAllLocations();
}
