package fr.diginamic.echolink.application.meteo.port.in;

import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;
import fr.diginamic.echolink.domain.meteo.Meteo;
import fr.diginamic.echolink.domain.meteo.exception.MeteoNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Use case for retrieving weather data associated with locations.
 */
public interface MeteoGetUseCase {

    /**
     * Retrieves the most recent weather record for the given location.
     *
     * @param locationId the unique identifier of the location
     * @return the latest weather record
     * @throws LocationNotFoundException if the location does not exist
     * @throws MeteoNotFoundException if no weather data exists for the location
     */
    Meteo getLastByLocationId(UUID locationId) throws LocationNotFoundException, MeteoNotFoundException;

    /**
     * Retrieves up to the configured maximum number of weather records
     * for the given location.
     *
     * @param locationId the unique identifier of the location
     * @return the weather records associated with the location
     * @throws LocationNotFoundException if the location does not exist
     * @throws MeteoNotFoundException if no weather data exists for the location
     */
    List<Meteo> getAllByLocationId(UUID locationId) throws LocationNotFoundException, MeteoNotFoundException;
}
