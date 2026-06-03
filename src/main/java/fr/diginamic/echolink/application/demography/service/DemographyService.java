package fr.diginamic.echolink.application.demography.service;

import fr.diginamic.echolink.application.demography.port.in.DemographyGetUseCase;
import fr.diginamic.echolink.application.demography.port.out.DemographyRepository;
import fr.diginamic.echolink.domain.demography.Demography;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DemographyService implements DemographyGetUseCase {

    private static final int LIMIT_DEMOGRAPHIC = 50;
    private final DemographyRepository repository;

    @Override
    public Demography getByLocationId(UUID id) {
        return repository.getByLocationId(id).orElse(null);
    }

    @Override
    public List<Demography> getAllByLocationId(UUID locationId) {
        return repository.getAllByLocationId(locationId, LIMIT_DEMOGRAPHIC);
    }
}
