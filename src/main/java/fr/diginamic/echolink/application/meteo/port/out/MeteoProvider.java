package fr.diginamic.echolink.application.meteo.port.out;

import fr.diginamic.echolink.domain.meteo.Meteo;

/**
 * Defines the contract for retrieving weather data from an external provider.
 */
public interface MeteoProvider {

    /**
     * Retrieves the current weather conditions for the specified coordinates.
     *
     * @param latitude latitude of the location
     * @param longitude longitude of the location
     * @return the current weather data
     */
    Meteo getCurrentWeather(double latitude, double longitude);
}
