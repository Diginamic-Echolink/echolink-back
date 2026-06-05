package fr.diginamic.echolink.infrastructure.meteo.in;

import fr.diginamic.echolink.application.meteo.service.MeteoSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled tasks responsible for triggering weather data synchronization.
 */
@Component
@RequiredArgsConstructor
public class MeteoScheduledTask {

    /**
     * Use case responsible for weather synchronization operations.
     */
    private final MeteoSyncService syncUseCase;

    /**
     * Initializes the daily synchronization queue.
     * Executed every day at 03:00 AM.
     */
    @Scheduled(cron = "0 0 3 * * *")
    public void startDailySync() {
        syncUseCase.initializeQueue();
    }

    /**
     * Processes a batch of weather synchronization operations.
     * Executed every two minutes.
     */
    @Scheduled(cron = "0 */2 * * * *")
    public void processBatch() {
        syncUseCase.syncTodayMeteo();
    }
}
