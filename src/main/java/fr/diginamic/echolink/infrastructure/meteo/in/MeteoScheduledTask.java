package fr.diginamic.echolink.infrastructure.meteo.in;

import fr.diginamic.echolink.application.meteo.service.MeteoSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeteoScheduledTask {

    private final MeteoSyncService syncUseCase;

    @Scheduled(cron = "0 0 3 * * *")
    public void startDailySync() {
        syncUseCase.initializeQueue();
    }

    @Scheduled(cron = "0 */2 * * * *")
    public void processBatch() {
        syncUseCase.syncTodayMeteo();
    }
}
