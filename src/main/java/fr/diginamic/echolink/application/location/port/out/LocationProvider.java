package fr.diginamic.echolink.application.location.port.out;

import fr.diginamic.echolink.domain.location.Location;

import java.util.List;

public interface LocationProvider {

    List<Location> getAllLocations();
}
