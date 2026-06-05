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

    @Override
    public Optional<Meteo> getLastMeteoByLocationId(UUID locationId) {
        return repository.findLastByLocationId(locationId);
    }

    @Override
    public List<Meteo> getAllMeteoByLocationId(UUID locationId, int limit) {
        return repository.findAllByLocationId(locationId, limit);
    }

    @Override
    public void saveAll(List<Meteo> meteos) {
        repository.saveAll(meteos);
    }
}
