package fr.diginamic.echolink.application.meteo.service;


import fr.diginamic.echolink.application.meteo.port.in.MeteoGetUseCase;
import fr.diginamic.echolink.application.meteo.port.out.MeteoRepository;
import fr.diginamic.echolink.domain.meteo.Meteo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MeteoService implements MeteoGetUseCase {

    private static final int LIMIT_METEO = 50;
    private final MeteoRepository repository;

    @Override
    public Meteo getMeteoByLocationId(UUID id) {
        return repository.getMeteoByLocationId(id).orElse(null);
    }

    @Override
    public List<Meteo> getAllMeteoByLocationId(UUID locationId) {
        return repository.getAllMeteoByLocationId(locationId, LIMIT_METEO);
    }
}

