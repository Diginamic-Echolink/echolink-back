package fr.diginamic.echolink.application.meteo.service;

import fr.diginamic.echolink.application.location.port.out.LocationRepository;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class MeteoSyncService implements MeteoSyncUseCase {

    private static final int BATCH_SIZE = 200;
    private volatile boolean syncRunning = false;

    private final LocationRepository locationRepository;
    private final MeteoRepository meteoRepository;
    private final MeteoProvider meteoProvider;

    private final Queue<Location> pendingLocations = new ConcurrentLinkedQueue<>();

    @Override
    public synchronized void initializeQueue() {

        if (!pendingLocations.isEmpty()) return;

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<Location> locationsToSync = locationRepository.getAllLocationsToSyncMeteoToday(startOfDay, endOfDay);

        pendingLocations.addAll(locationsToSync);

        if (!locationsToSync.isEmpty()) {
            syncRunning = true;
        }

        log.info("Weather synchronization for today: {} Locations loaded", locationsToSync.size());
    }

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
                        "Skipping location due to API failure. Unable to retrieve weather datas for {} ({})",
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
