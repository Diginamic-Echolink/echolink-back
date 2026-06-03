package fr.diginamic.echolink.application.meteo.port.in;

import fr.diginamic.echolink.domain.meteo.Meteo;

import java.util.List;
import java.util.UUID;

public interface MeteoGetUseCase {

    Meteo getMeteoByLocationId(UUID locationId);

    List<Meteo> getAllMeteoByLocationId(UUID locationId);
}
