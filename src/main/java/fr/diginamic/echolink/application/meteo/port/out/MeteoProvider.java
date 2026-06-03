package fr.diginamic.echolink.application.meteo.port.out;

import fr.diginamic.echolink.domain.meteo.Meteo;

public interface MeteoProvider {

    Meteo getCurrentWeather(double latitude, double longitude);
}
