package fr.diginamic.echolink.infrastructure.common.in;

import fr.diginamic.echolink.application.airquality.port.in.AirQualitySyncUseCase;
import fr.diginamic.echolink.application.location.port.in.LocationSyncUseCase;
import fr.diginamic.echolink.application.meteo.port.in.MeteoSyncUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartupInitializer {

    private final LocationSyncUseCase locationSyncUseCase;
    private final MeteoSyncUseCase meteoSyncUseCase;
    private final AirQualitySyncUseCase airQualitySyncUseCase;

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        locationSyncUseCase.syncLocations();
        meteoSyncUseCase.initializeQueue();
        airQualitySyncUseCase.initializeQueue();
    }
}
