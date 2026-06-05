package fr.diginamic.echolink.infrastructure.airquality.in;

import fr.diginamic.echolink.application.airquality.port.in.AirQualitySyncUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled task responsible for triggering air quality synchronization.
 * Initializes the synchronization queue daily and processes synchronization
 * batches at regular intervals.
 */
@Component
@RequiredArgsConstructor
public class AirQualityScheduledTask {

    /**
     * Use case responsible for air quality synchronization.
     */
    private final AirQualitySyncUseCase syncUseCase;

    /**
     * Initializes the synchronization queue every day at 04:00.
     */
    @Scheduled(cron = "0 0 4 * * *")
    public void startDailySync() {
        syncUseCase.initializeQueue();
    }

    /**
     * Processes a synchronization batch every two minutes and thirty seconds.
     */
    @Scheduled(cron = "30 */2 * * * *")
    public void processBatch() {
        syncUseCase.syncTodayAirQuality();
    }
}
