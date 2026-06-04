package fr.diginamic.echolink.application.meteo.port.in;

import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;
import fr.diginamic.echolink.domain.meteo.Meteo;

import java.util.List;
import java.util.UUID;

public interface MeteoGetUseCase {

    Meteo getMeteoByLocationId(UUID locationId) throws LocationNotFoundException;

    List<Meteo> getAllMeteoByLocationId(UUID locationId);
}
