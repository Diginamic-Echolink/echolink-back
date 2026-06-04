package fr.diginamic.echolink.infrastructure.airquality.out.persistence;

import fr.diginamic.echolink.application.airquality.port.out.AirQualityRepository;
import fr.diginamic.echolink.domain.airquality.AirQuality;
import fr.diginamic.echolink.infrastructure.airquality.out.persistence.repository.AirQualityJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AirQualityRepositoryAdapter implements AirQualityRepository {

    private final AirQualityJdbcRepository repository;

    @Override
    public Optional<AirQuality> getByLocationId(UUID locationId) {
        return repository.findByLocationId(locationId);
    }

    @Override
    public List<AirQuality> getAllByLocationId(UUID locationId, int limit) {
        return repository.findAllByLocationId(locationId, limit);
    }

    @Override
    public void saveAll(List<AirQuality> airQualities) {
        repository.saveAll(airQualities);
    }
}
