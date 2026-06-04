package fr.diginamic.echolink.application.location.port.in;

import fr.diginamic.echolink.domain.location.Location;

import java.util.List;
import java.util.UUID;

public interface LocationGetUseCase {

    Location getById(UUID id);

    List<Location> getByGeo(double latitude, double longitude, int delta);
}
