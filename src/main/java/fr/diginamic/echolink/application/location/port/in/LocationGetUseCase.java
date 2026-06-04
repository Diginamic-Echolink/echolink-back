package fr.diginamic.echolink.application.location.port.in;

import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;

import java.util.List;
import java.util.UUID;

public interface LocationGetUseCase {

    Location getById(UUID id) throws LocationNotFoundException;

    List<Location> getByGeo(double latitude, double longitude, int delta);
}
