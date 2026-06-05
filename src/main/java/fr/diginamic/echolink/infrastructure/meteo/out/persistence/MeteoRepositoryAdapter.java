package fr.diginamic.echolink.infrastructure.meteo.out.persistence;


import fr.diginamic.echolink.application.meteo.port.out.MeteoRepository;
import fr.diginamic.echolink.domain.meteo.Meteo;
import fr.diginamic.echolink.infrastructure.meteo.out.persistence.repository.MeteoJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Adapter implementing the weather repository port using a JPA repository.
 */
@Component
@RequiredArgsConstructor
public class MeteoRepositoryAdapter implements MeteoRepository {

    /**
     * Repository used to access weather persistence data.
     */
    private final MeteoJdbcRepository repository;

    /**
     * Retrieves the most recent weather record associated with a location.
     *
     * @param locationId unique identifier of the location
     * @return an {@link Optional} containing the latest weather record if found
     */
    @Override
    public Optional<Meteo> getLastMeteoByLocationId(UUID locationId) {
        return repository.findLastByLocationId(locationId);
    }

    /**
     * Retrieves a limited number of weather records associated with a location.
     *
     * @param locationId unique identifier of the location
     * @param limit maximum number of weather records to retrieve
     * @return list of weather records associated with the location
     */
    @Override
    public List<Meteo> getAllMeteoByLocationId(UUID locationId, int limit) {
        return repository.findAllByLocationId(locationId, limit);
    }

    /**
     * Persists a collection of weather records.
     *
     * @param meteos weather records to save
     */
    @Override
    public void saveAll(List<Meteo> meteos) {
        repository.saveAll(meteos);
    }
}
