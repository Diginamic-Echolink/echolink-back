package fr.diginamic.echolink.infrastructure.meteo.out.persistence;


import fr.diginamic.echolink.application.meteo.port.out.MeteoRepository;
import fr.diginamic.echolink.domain.meteo.Meteo;
import fr.diginamic.echolink.infrastructure.meteo.out.persistence.repository.MeteoJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MeteoRepositoryAdapter implements MeteoRepository {

    private final MeteoJdbcRepository repository;

    @Override
    public Optional<Meteo> getMeteoByLocationId(UUID locationId) {
        return repository.findByLocationId(locationId);
    }

    @Override
    public List<Meteo> getAllMeteoByLocationId(UUID locationId, int limit) {
        return repository.findAllByLocationId(locationId, limit);
    }
}
