package fr.diginamic.echolink.application.location.port.in;

/**
 * Defines the use case for synchronizing location data.
 */
public interface LocationSyncUseCase {

    /**
     * Synchronizes location data from the configured data source.
     */
    void syncLocations();
}
