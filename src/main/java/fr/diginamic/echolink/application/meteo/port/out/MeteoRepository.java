package fr.diginamic.echolink.application.meteo.port.out;

import fr.diginamic.echolink.domain.meteo.Meteo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Defines the contract for accessing and persisting weather data.
 */
public interface MeteoRepository {

    /**
     * Retrieves the most recent weather record for the specified location.
     *
     * @param locationId unique identifier of the location
     * @return an {@link Optional} containing the latest weather record if found
     */
    Optional<Meteo> getLastMeteoByLocationId(UUID locationId);

    /**
     * Retrieves a limited list of weather records for the specified location.
     *
     * @param locationId unique identifier of the location
     * @param LIMIT_METEO maximum number of weather records to retrieve
     * @return a list of weather records associated with the location
     */
    List<Meteo> getAllMeteoByLocationId(UUID locationId, int LIMIT_METEO);

    /**
     * Persists a collection of weather records.
     *
     * @param meteos list of weather records to save
     */
    void saveAll(List<Meteo> meteos);
}
