package fr.diginamic.echolink.application.airquality.port.in;

public interface AirQualitySyncUseCase {

    void initializeQueue();

    void syncTodayAirQuality();
}
