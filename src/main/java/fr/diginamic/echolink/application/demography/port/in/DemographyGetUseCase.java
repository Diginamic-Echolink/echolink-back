package fr.diginamic.echolink.application.demography.port.in;

import fr.diginamic.echolink.domain.demography.Demography;

import java.util.List;
import java.util.UUID;

public interface DemographyGetUseCase {

    Demography getByLocationId(UUID locationId);

    List<Demography> getAllByLocationId(UUID locationId);
}
