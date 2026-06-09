package fr.diginamic.echolink.application.meteo.service;

import fr.diginamic.echolink.application.location.port.in.LocationGetUseCase;
import fr.diginamic.echolink.application.meteo.port.in.MeteoGetUseCase;
import fr.diginamic.echolink.application.meteo.port.out.MeteoRepository;
import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;
import fr.diginamic.echolink.domain.meteo.Meteo;
import fr.diginamic.echolink.domain.meteo.exception.MeteoNotFoundException;
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
     * Use case used to retrieve locations.
     */
    private final LocationGetUseCase locationGetUseCase;

    /**
     * Repository used to access weather data.
     */
    private final MeteoRepository repository;

    @Override
    public Meteo getLastByLocationId(UUID locationId)
            throws LocationNotFoundException, MeteoNotFoundException {

        validateLocationExists(locationId);

        return repository.getLastByLocationId(locationId)
                .orElseThrow(() -> new MeteoNotFoundException(
                        "No weather data found for location with id " + locationId));
    }

    @Override
    public List<Meteo> getAllByLocationId(UUID locationId)
            throws LocationNotFoundException, MeteoNotFoundException {

        validateLocationExists(locationId);

        List<Meteo> meteos = repository.getAllByLocationId(locationId, LIMIT_METEO);

        if (meteos.isEmpty()) {
            throw new MeteoNotFoundException(
                    "No weather data found for location with id " + locationId);
        }

        return meteos;
    }

    private void validateLocationExists(UUID locationId) throws LocationNotFoundException {

        if (!locationGetUseCase.existsById(locationId)) {
            throw new LocationNotFoundException(
                    "Location with id " + locationId + " doesn't exist");
        }
    }
}
