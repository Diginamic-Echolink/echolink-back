package fr.diginamic.echolink.infrastructure.location.in;

import fr.diginamic.echolink.application.location.port.in.LocationSyncUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationScheduledTask {

    private final LocationSyncUseCase syncUseCase;

    @Scheduled(cron = "0 0 2 1 * *")
    public void sync() {
        syncUseCase.syncLocations();
    }
}
