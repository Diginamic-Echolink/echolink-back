package fr.diginamic.echolink.infrastructure.common.in;

import fr.diginamic.echolink.application.airquality.port.in.AirQualitySyncUseCase;
import fr.diginamic.echolink.application.location.port.in.LocationSyncUseCase;
import fr.diginamic.echolink.application.meteo.port.in.MeteoSyncUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Component responsible for initializing application data
 * and synchronization processes at startup.
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.startup-initializer.enabled", havingValue = "true")
public class StartupInitializer {

    private final LocationSyncUseCase locationSyncUseCase;
    private final MeteoSyncUseCase meteoSyncUseCase;
    private final AirQualitySyncUseCase airQualitySyncUseCase;

    /**
     * Executes startup initialization tasks once the application
     * is fully ready.
     * <p>
     * Synchronizes locations and initializes weather and air quality
     * synchronization queues.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        locationSyncUseCase.syncLocations();
        meteoSyncUseCase.initializeQueue();
        airQualitySyncUseCase.initializeQueue();
    }
}
