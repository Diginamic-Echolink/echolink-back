package fr.diginamic.echolink.application.meteo.port.in;

/**
 * Use case for synchronizing weather data.
 * Provides operations to initialize synchronization resources
 * and retrieve the latest weather information.
 */
public interface MeteoSyncUseCase {

    /**
     * Initializes the queue used for weather synchronization.
     */
    void initializeQueue();

    /**
     * Synchronizes the current day's weather data.
     */
    void syncTodayMeteo();
}
