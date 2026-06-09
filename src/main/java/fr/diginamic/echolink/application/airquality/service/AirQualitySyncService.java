package fr.diginamic.echolink.application.airquality.service;

import fr.diginamic.echolink.application.airquality.port.in.AirQualitySyncUseCase;
import fr.diginamic.echolink.application.airquality.port.out.AirQualityProvider;
import fr.diginamic.echolink.application.airquality.port.out.AirQualityRepository;
import fr.diginamic.echolink.application.location.port.in.LocationGetUseCase;
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

/**
 * Service responsible for synchronizing air quality data.
 * Loads locations that require synchronization, retrieves air quality data
 * from an external provider, and persists the collected records.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AirQualitySyncService implements AirQualitySyncUseCase {

    /**
     * Maximum number of locations processed during one synchronization execution.
     */
    private static final int BATCH_SIZE = 200;

    /**
     * Indicates whether an air quality synchronization is currently running.
     */
    private volatile boolean syncRunning = false;

    /**
     * Use case used to retrieve locations.
     */
    private final LocationGetUseCase locationGetUseCase;

    /**
     * Repository used to persist air quality records.
     */
    private final AirQualityRepository airQualityRepository;

    /**
     * Provider used to retrieve current air quality data from an external API.
     */
    private final AirQualityProvider airQualityProvider;

    /**
     * Queue containing locations waiting to be synchronized.
     */
    private final Queue<Location> pendingLocations = new ConcurrentLinkedQueue<>();

    /**
     * Initializes the queue with locations that require air quality synchronization for the current day.
     * If the queue already contains locations, the initialization is skipped.
     */
    @Override
    public synchronized void initializeQueue() {

        if (!pendingLocations.isEmpty()) return;

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<Location> locationsToSync = locationGetUseCase.getAllLocationsToSyncAirQualityToday(startOfDay, endOfDay);

        pendingLocations.addAll(locationsToSync);

        if (!locationsToSync.isEmpty()) {
            syncRunning = true;
        }

        log.info("Air quality synchronization for today: {} locations loaded", locationsToSync.size());
    }

    /**
     * Synchronizes air quality data for the current day.
     * Processes locations in batches, retrieves their air quality data,
     * saves the collected records, and stops the synchronization when the queue is empty.
     */
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
                        "Skipping location {} ({}) because air quality provider returned an error: {}",
                        location.getName(),
                        location.getInseeCode(),
                        ex.getMessage()
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
