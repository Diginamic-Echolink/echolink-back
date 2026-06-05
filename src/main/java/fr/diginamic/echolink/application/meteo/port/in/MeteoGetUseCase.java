package fr.diginamic.echolink.application.meteo.port.in;

import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;
import fr.diginamic.echolink.domain.meteo.Meteo;

import java.util.List;
import java.util.UUID;

/**
 * Use case for retrieving weather data associated with locations.
 */
public interface MeteoGetUseCase {

    /**
     * Retrieves the most recent weather record for a location.
     *
     * @param locationId unique identifier of the location
     * @return the latest weather record associated with the location
     * @throws LocationNotFoundException if the location does not exist
     */
    Meteo getLastMeteoByLocationId(UUID locationId) throws LocationNotFoundException;

    /**
     * Retrieves all available weather records for a location.
     *
     * @param locationId unique identifier of the location
     * @return a list of weather records associated with the location
     */
    List<Meteo> getAllMeteoByLocationId(UUID locationId);
}
