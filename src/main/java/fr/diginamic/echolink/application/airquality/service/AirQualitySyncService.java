package fr.diginamic.echolink.application.airquality.service;

import fr.diginamic.echolink.application.airquality.port.in.AirQualitySyncUseCase;
import fr.diginamic.echolink.application.airquality.port.out.AirQualityProvider;
import fr.diginamic.echolink.application.airquality.port.out.AirQualityRepository;
import fr.diginamic.echolink.application.location.port.out.LocationRepository;
import fr.diginamic.echolink.domain.airquality.AirQuality;
import fr.diginamic.echolink.domain.airquality.exception.AirQualityApiSyncException;
import fr.diginamic.echolink.domain.location.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Service
@RequiredArgsConstructor
public class AirQualitySyncService implements AirQualitySyncUseCase {

    private static final int BATCH_SIZE = 200;
    private volatile boolean syncRunning = false;

    private final LocationRepository locationRepository;
    private final AirQualityRepository airQualityRepository;
    private final AirQualityProvider airQualityProvider;

    private final Queue<Location> pendingLocations = new ConcurrentLinkedQueue<>();

    @Override
    public synchronized void initializeQueue() {

        if (!pendingLocations.isEmpty()) return;

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<Location> locationsToSync = locationRepository.findLocationToSyncAirQualityToday(startOfDay, endOfDay);

        pendingLocations.addAll(locationsToSync);
        syncRunning = true;

        log.info("Air quality synchronization for today: {} locations loaded", locationsToSync.size());
    }

    @Override
    public void syncTodayAirQuality() {

        if (!syncRunning) return;

        long startTime = System.currentTimeMillis();
        log.info("Air quality synchronization started");

        List<AirQuality> airQualities = new ArrayList<>();
        int processed = 0;

        while (!pendingLocations.isEmpty() && processed < BATCH_SIZE) {

            Location location = pendingLocations.poll();
            if (location == null) break;

            try {

                AirQuality airQuality =
                        airQualityProvider.getCurrentAirQuality(
                                location.getLatitude(),
                                location.getLongitude()
                        );

                if (airQuality != null) {
                    airQuality.setLocation(location);
                    airQualities.add(airQuality);
                }

                processed++;

            } catch (AirQualityApiSyncException ex) {
                log.warn(
                        "Skipping location due to API failure. Unable to retrieve air quality datas for {} ({})",
                        location.getName(),
                        location.getInseeCode(),
                        ex
                );
            }
        }

        if (!airQualities.isEmpty()) {
            airQualityRepository.saveAll(airQualities);
        }

        log.info("Processed {} air quality records in {}s. Remaining: {}",
                processed,
                String.format("%.2f", (System.currentTimeMillis() - startTime) / 1000d),
                pendingLocations.size()
        );

        if (pendingLocations.isEmpty()) {
            syncRunning = false;
            log.info("Air quality synchronization completed");
        }
    }
}
