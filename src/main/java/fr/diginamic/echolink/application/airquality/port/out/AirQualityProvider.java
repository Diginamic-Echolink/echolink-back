package fr.diginamic.echolink.application.airquality.port.out;

import fr.diginamic.echolink.domain.airquality.AirQuality;

/**
 * Output port for retrieving air quality data from an external provider.
 */
public interface AirQualityProvider {

    /**
     * Retrieves the current air quality data for the specified coordinates.
     *
     * @param latitude latitude of the location
     * @param longitude longitude of the location
     * @return the current air quality data
     */
    AirQuality getCurrentAirQuality(double latitude, double longitude);
}
