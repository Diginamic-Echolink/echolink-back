package fr.diginamic.echolink.infrastructure.airquality.in;

import fr.diginamic.echolink.application.airquality.port.in.AirQualitySyncUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AirQualityScheduledTask {

    private final AirQualitySyncUseCase syncUseCase;

    @Scheduled(cron = "0 0 4 * * *")
    public void startDailySync() {
        syncUseCase.initializeQueue();
    }

    @Scheduled(cron = "30 */2 * * * *")
    public void processBatch() {
        syncUseCase.syncTodayAirQuality();
    }
}
