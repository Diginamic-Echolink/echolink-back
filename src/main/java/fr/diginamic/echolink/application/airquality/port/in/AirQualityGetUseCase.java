package fr.diginamic.echolink.application.airquality.port.in;

import fr.diginamic.echolink.domain.airquality.AirQuality;
import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;

import java.util.List;
import java.util.UUID;

public interface AirQualityGetUseCase {

    AirQuality getByLocationId(UUID locationId) throws LocationNotFoundException;

    List<AirQuality> getAllByLocationId(UUID locationId);
}
