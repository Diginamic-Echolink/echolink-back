package fr.diginamic.echolink.application.airquality.port.out;

import fr.diginamic.echolink.domain.airquality.AirQuality;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AirQualityRepository {

    Optional<AirQuality> getByLocationId(UUID locationId );

    List<AirQuality> getAllByLocationId(UUID locationId, int LIMIT_AIR_QUALITY);

    void saveAll(List<AirQuality> airQualities);
}
