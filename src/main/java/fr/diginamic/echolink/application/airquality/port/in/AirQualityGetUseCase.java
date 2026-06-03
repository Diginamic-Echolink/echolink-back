package fr.diginamic.echolink.application.airquality.port.in;

import fr.diginamic.echolink.domain.airquality.AirQuality;

import java.util.List;
import java.util.UUID;

public interface AirQualityGetUseCase {

    AirQuality getByLocationId(UUID locationId);

    List<AirQuality> getAllByLocationId(UUID locationId);
}
