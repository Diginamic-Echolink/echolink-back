package fr.diginamic.echolink.application.meteo.service;

import fr.diginamic.echolink.application.location.port.in.LocationGetUseCase;
import fr.diginamic.echolink.application.meteo.port.in.MeteoSyncUseCase;
import fr.diginamic.echolink.application.meteo.port.out.MeteoProvider;
import fr.diginamic.echolink.application.meteo.port.out.MeteoRepository;
import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.domain.meteo.Meteo;
import fr.diginamic.echolink.domain.meteo.exception.MeteoApiSyncException;
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
 * Service responsible for synchronizing weather data for locations.
 * This service loads locations that require weather updates, retrieves
 * current weather data from an external provider and persists the results
 * in batches.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MeteoSyncService implements MeteoSyncUseCase {

    /**
     * Maximum number of locations processed during a synchronization batch.
     */
    private static final int BATCH_SIZE = 200;

    /**
     * Indicates whether a synchronization process is currently running.
     */
    private volatile boolean syncRunning = false;

    /**
     * Use case used to retrieve locations.
     */
    private final LocationGetUseCase locationGetUseCase;

    /**
     * Repository used to persist weather data.
     */
    private final MeteoRepository meteoRepository;

    /**
     * Provider used to retrieve weather data from an external source.
     */
    private final MeteoProvider meteoProvider;

    /**
     * Queue containing locations waiting to be synchronized.
     */
    private final Queue<Location> pendingLocations = new ConcurrentLinkedQueue<>();

    /**
     * Initializes the synchronization queue with locations that have not yet
     * received weather data for the current day.
     */
    @Override
    public synchronized void initializeQueue() {

        if (!pendingLocations.isEmpty()) return;

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<Location> locationsToSync = locationGetUseCase.getAllLocationsToSyncMeteoToday(startOfDay, endOfDay);

        pendingLocations.addAll(locationsToSync);

        if (!locationsToSync.isEmpty()) {
            syncRunning = true;
        }

        log.info("Weather synchronization for today: {} Locations loaded", locationsToSync.size());
    }

    /**
     * Processes a batch of locations and synchronizes their current weather data.
     * Retrieved weather records are persisted once the batch is completed.
     */
    @Override
    public void syncTodayMeteo() {

        if (!syncRunning) return;

        long currentTimeMillis = System.currentTimeMillis();
        log.info("Weather datas sync started");

        List<Meteo> meteos = new ArrayList<>();
        int processed = 0;

        while (!pendingLocations.isEmpty() && processed < BATCH_SIZE) {

            Location location = pendingLocations.poll();
            if (location == null) break;

            try {

                Meteo meteo = meteoProvider.getCurrentWeather(location.getLatitude(), location.getLongitude());

                if (meteo != null) {
                    meteo.setLocation(location);
                    meteos.add(meteo);
                }

                processed++;

            } catch (MeteoApiSyncException ex) {
                log.warn(
                        "Skipping location {} ({}) because weather provider returned an error.",
                        location.getName(),
                        location.getInseeCode(),
                        ex
                );
            }
        }

        if (!meteos.isEmpty()) {
            meteoRepository.saveAll(meteos);
        }

        log.info("Processed {} weather datas in {}s. Remaining: {}",
                processed,
                String.format("%.2f", (System.currentTimeMillis() - currentTimeMillis) / 1000d),
                pendingLocations.size()
        );

        if (pendingLocations.isEmpty()) {
            syncRunning = false;
            log.info("Weather synchronization completed");
        }
    }
}
