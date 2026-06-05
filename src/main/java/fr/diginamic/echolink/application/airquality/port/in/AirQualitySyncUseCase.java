package fr.diginamic.echolink.application.airquality.port.in;

/**
 * Use case for synchronizing air quality data.
 */
public interface AirQualitySyncUseCase {

    /**
     * Initializes the queue used for air quality synchronization.
     */
    void initializeQueue();

    /**
     * Synchronizes the current day's air quality data.
     */
    void syncTodayAirQuality();
}
