package fr.diginamic.echolink.application.meteo.service;

import fr.diginamic.echolink.application.meteo.port.in.MeteoGetUseCase;
import fr.diginamic.echolink.application.meteo.port.out.MeteoRepository;
import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;
import fr.diginamic.echolink.domain.meteo.Meteo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service implementation for weather data retrieval use cases.
 * Provides access to weather information associated with locations.
 */
@Service
@RequiredArgsConstructor
public class MeteoService implements MeteoGetUseCase {

    /**
     * Maximum number of weather records returned for a location.
     */
    private static final int LIMIT_METEO = 50;

    /**
     * Repository used to access weather data.
     */
    private final MeteoRepository repository;

    @Override
    public Meteo getLastMeteoByLocationId(UUID locationId) throws LocationNotFoundException {
        return repository.getLastMeteoByLocationId(locationId)
                .orElseThrow(() -> new LocationNotFoundException("Location with id " + locationId + " not found"));
    }

    @Override
    public List<Meteo> getAllMeteoByLocationId(UUID locationId) {
        return repository.getAllMeteoByLocationId(locationId, LIMIT_METEO);
    }
}
