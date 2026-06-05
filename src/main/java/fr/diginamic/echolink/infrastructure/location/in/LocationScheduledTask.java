package fr.diginamic.echolink.infrastructure.location.in;

import fr.diginamic.echolink.application.location.port.in.LocationSyncUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Scheduled task responsible for triggering location synchronization.
 */
@Service
@RequiredArgsConstructor
public class LocationScheduledTask {

    /**
     * Use case responsible for location synchronization operations.
     */
    private final LocationSyncUseCase syncUseCase;

    /**
     * Triggers the synchronization of location data.
     * Executed on the first day of every month at 02:00 AM.
     */
    @Scheduled(cron = "0 0 2 1 * *")
    public void sync() {
        syncUseCase.syncLocations();
    }
}
