package fr.diginamic.echolink.application.airquality.port.out;

import fr.diginamic.echolink.domain.airquality.AirQuality;

public interface AirQualityProvider {

    AirQuality getCurrentAirQuality(double latitude, double longitude);
}
