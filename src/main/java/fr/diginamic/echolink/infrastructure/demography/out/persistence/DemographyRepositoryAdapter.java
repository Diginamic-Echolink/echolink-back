package fr.diginamic.echolink.infrastructure.demography.out.persistence;


import fr.diginamic.echolink.application.demography.port.out.DemographyRepository;
import fr.diginamic.echolink.domain.demography.Demography;
import fr.diginamic.echolink.infrastructure.demography.out.persistence.repository.DemographyJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DemographyRepositoryAdapter implements DemographyRepository {
    private final DemographyJdbcRepository repository;

    @Override
    public Optional<Demography> getByLocationId(UUID locationId) {
        return repository.getByLocationId(locationId);
    }

    @Override
    public List<Demography> getAllByLocationId(UUID locationId, int limit) {
        return repository.getAllByLocationId(locationId, limit);
    }
}
