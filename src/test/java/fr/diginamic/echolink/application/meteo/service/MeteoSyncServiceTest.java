package fr.diginamic.echolink.application.meteo.service;

import fr.diginamic.echolink.application.location.port.out.LocationRepository;
import fr.diginamic.echolink.application.meteo.port.out.MeteoProvider;
import fr.diginamic.echolink.application.meteo.port.out.MeteoRepository;
import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.domain.meteo.Meteo;
import fr.diginamic.echolink.domain.meteo.exception.MeteoApiSyncException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static fr.diginamic.echolink.domain.location.LocationTestData.givenLocation1;
import static fr.diginamic.echolink.domain.meteo.MeteoTestData.givenMeteo1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MeteoSyncServiceTest {

    @Mock
    private LocationRepository locationRepository;
    @Mock
    private MeteoRepository meteoRepository;
    @Mock
    private MeteoProvider meteoProvider;
    @InjectMocks
    private MeteoSyncService service;

    @Test
    void should_initialize_queue_with_locations_to_sync() {
        // GIVEN
        Location location = givenLocation1();

        when(locationRepository.getAllLocationsToSyncMeteoToday(any(), any()))
                .thenReturn(List.of(location));

        // WHEN
        service.initializeQueue();

        // THEN
        verify(locationRepository).getAllLocationsToSyncMeteoToday(any(), any());
    }

    @Test
    void should_not_reload_queue_if_already_initialized() {
        // GIVEN
        Location location = givenLocation1();

        when(locationRepository.getAllLocationsToSyncMeteoToday(any(), any()))
                .thenReturn(List.of(location));

        service.initializeQueue();
        service.initializeQueue();

        // THEN
        verify(locationRepository, times(1))
                .getAllLocationsToSyncMeteoToday(any(), any());
    }

    @Test
    void should_process_and_save_meteo_data() {
        // GIVEN
        Location location = givenLocation1();
        Meteo meteo = givenMeteo1();

        when(locationRepository.getAllLocationsToSyncMeteoToday(any(), any()))
                .thenReturn(List.of(location));

        when(meteoProvider.getCurrentWeather(location.getLatitude(), location.getLongitude()))
                .thenReturn(meteo);

        service.initializeQueue();

        // WHEN
        service.syncTodayMeteo();

        // THEN
        verify(meteoProvider).getCurrentWeather(location.getLatitude(), location.getLongitude());
        verify(meteoRepository).saveAll(List.of(meteo));
    }

    @Test
    void should_skip_location_when_api_fails() {
        // GIVEN
        Location location = givenLocation1();

        when(locationRepository.getAllLocationsToSyncMeteoToday(any(), any()))
                .thenReturn(List.of(location));

        when(meteoProvider.getCurrentWeather(anyDouble(), anyDouble()))
                .thenThrow(new MeteoApiSyncException("API error"));

        service.initializeQueue();

        // WHEN
        service.syncTodayMeteo();

        // THEN
        verify(meteoRepository, never()).saveAll(anyList());
    }

    @Test
    void should_respect_batch_size_limit() {
        // GIVEN
        List<Location> locations = new ArrayList<>();

        for (int i = 0; i < 250; i++) {
            locations.add(givenLocation1());
        }

        when(locationRepository.getAllLocationsToSyncMeteoToday(any(), any()))
                .thenReturn(locations);

        when(meteoProvider.getCurrentWeather(anyDouble(), anyDouble()))
                .thenReturn(givenMeteo1());

        service.initializeQueue();

        // WHEN
        service.syncTodayMeteo();

        // THEN
        verify(meteoProvider, atMost(200))
                .getCurrentWeather(anyDouble(), anyDouble());
    }

    @Test
    void should_stop_sync_when_queue_empty() {
        // GIVEN
        Location location = givenLocation1();

        when(locationRepository.getAllLocationsToSyncMeteoToday(any(), any()))
                .thenReturn(List.of(location));

        when(meteoProvider.getCurrentWeather(anyDouble(), anyDouble()))
                .thenReturn(givenMeteo1());

        service.initializeQueue();

        // WHEN
        service.syncTodayMeteo();
        service.syncTodayMeteo();

        // THEN
        verify(meteoProvider, times(1)).getCurrentWeather(anyDouble(), anyDouble());
    }
}
