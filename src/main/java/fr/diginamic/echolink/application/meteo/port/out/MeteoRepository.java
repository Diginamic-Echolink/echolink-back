package fr.diginamic.echolink.application.meteo.port.out;

import fr.diginamic.echolink.domain.meteo.Meteo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MeteoRepository {

    Optional<Meteo> getLastMeteoByLocationId(UUID locationId);

    List<Meteo> getAllMeteoByLocationId(UUID locationId, int LIMIT_METEO);

    void saveAll(List<Meteo> meteos);
}
