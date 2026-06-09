package fr.diginamic.echolink.infrastructure.airquality.out.persistence;

import fr.diginamic.echolink.application.airquality.port.out.AirQualityRepository;
import fr.diginamic.echolink.domain.airquality.AirQuality;
import fr.diginamic.echolink.infrastructure.airquality.out.persistence.repository.AirQualityJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Adapter implementation of the {@link AirQualityRepository} output port.
 * Delegates persistence operations to the underlying JPA repository.
 */
@Component
@RequiredArgsConstructor
public class AirQualityRepositoryAdapter implements AirQualityRepository {

    /**
     * JPA repository used to access air quality data.
     */
    private final AirQualityJdbcRepository repository;

    /**
     * Retrieves the most recent air quality record associated with a location.
     *
     * @param locationId unique identifier of the location
     * @return an optional containing the air quality record if found
     */
    @Override
    public Optional<AirQuality> getLastByLocationId(UUID locationId) {
        return repository.findByLocationId(locationId);
    }

    /**
     * Retrieves a limited number of air quality records associated with a location.
     *
     * @param locationId unique identifier of the location
     * @param limit maximum number of records to retrieve
     * @return a list of air quality records associated with the location
     */
    @Override
    public List<AirQuality> getAllByLocationId(UUID locationId, int limit) {
        return repository.findAllByLocationId(locationId, limit);
    }

    /**
     * Persists a collection of air quality records.
     *
     * @param airQualities list of air quality records to save
     */
    @Override
    public void saveAll(List<AirQuality> airQualities) {
        repository.saveAll(airQualities);
    }
}
