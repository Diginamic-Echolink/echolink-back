package fr.diginamic.echolink.application.demography.port.out;

import fr.diginamic.echolink.domain.demography.Demography;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DemographyRepository {

    Optional<Demography> getByLocationId(UUID id);

    List<Demography> getAllByLocationId(UUID id, int LIMIT_DEMOGRAPHIC);
}

