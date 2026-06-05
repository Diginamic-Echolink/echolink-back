package fr.diginamic.echolink.application.airquality.service;

import fr.diginamic.echolink.application.airquality.port.out.AirQualityProvider;
import fr.diginamic.echolink.application.airquality.port.out.AirQualityRepository;
import fr.diginamic.echolink.application.location.port.out.LocationRepository;
import fr.diginamic.echolink.domain.airquality.AirQuality;
import fr.diginamic.echolink.domain.airquality.exception.AirQualityApiSyncException;
import fr.diginamic.echolink.domain.location.Location;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static fr.diginamic.echolink.domain.airquality.AirQualityTestData.givenAirQuality1;
import static fr.diginamic.echolink.domain.location.LocationTestData.givenLocation1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AirQualitySyncServiceTest {

    @Mock
    private LocationRepository locationRepository;
    @Mock
    private AirQualityRepository airQualityRepository;
    @Mock
    private AirQualityProvider airQualityProvider;
    @InjectMocks
    private AirQualitySyncService service;

    @Test
    void should_initialize_queue_with_locations_to_sync() {
        // GIVEN
        Location location = givenLocation1();

        when(locationRepository.getAllLocationsToSyncAirQualityToday(any(), any()))
                .thenReturn(List.of(location));

        // WHEN
        service.initializeQueue();

        // THEN
        verify(locationRepository).getAllLocationsToSyncAirQualityToday(any(), any());
    }

    @Test
    void should_not_reinitialize_queue_if_already_initialized() {
        // GIVEN
        Location location = givenLocation1();

        when(locationRepository.getAllLocationsToSyncAirQualityToday(any(), any()))
                .thenReturn(List.of(location));

        service.initializeQueue();
        service.initializeQueue();

        // THEN
        verify(locationRepository, times(1))
                .getAllLocationsToSyncAirQualityToday(any(), any());
    }

    @Test
    void should_process_and_save_air_quality() {
        // GIVEN
        Location location = givenLocation1();
        AirQuality airQuality = givenAirQuality1();

        when(locationRepository.getAllLocationsToSyncAirQualityToday(any(), any()))
                .thenReturn(List.of(location));

        when(airQualityProvider.getCurrentAirQuality(location.getLatitude(), location.getLongitude()))
                .thenReturn(airQuality);

        service.initializeQueue();

        // WHEN
        service.syncTodayAirQuality();

        // THEN
        verify(airQualityProvider).getCurrentAirQuality(location.getLatitude(), location.getLongitude());
        verify(airQualityRepository).saveAll(List.of(airQuality));
    }

    @Test
    void should_skip_location_when_api_fails() {
        // GIVEN
        Location location = givenLocation1();

        when(locationRepository.getAllLocationsToSyncAirQualityToday(any(), any()))
                .thenReturn(List.of(location));

        when(airQualityProvider.getCurrentAirQuality(anyDouble(), anyDouble()))
                .thenThrow(new AirQualityApiSyncException("API error"));

        service.initializeQueue();

        // WHEN
        service.syncTodayAirQuality();

        // THEN
        verify(airQualityRepository, never()).saveAll(anyList());
    }

    @Test
    void should_respect_batch_size_limit() {
        // GIVEN
        List<Location> locations = new ArrayList<>();

        for (int i = 0; i < 250; i++) {
            locations.add(givenLocation1());
        }

        when(locationRepository.getAllLocationsToSyncAirQualityToday(any(), any()))
                .thenReturn(locations);

        when(airQualityProvider.getCurrentAirQuality(anyDouble(), anyDouble()))
                .thenReturn(givenAirQuality1());

        service.initializeQueue();

        // WHEN
        service.syncTodayAirQuality();

        // THEN
        verify(airQualityProvider, atMost(200)).getCurrentAirQuality(anyDouble(), anyDouble());
    }

    @Test
    void should_stop_sync_when_queue_is_empty() {
        // GIVEN
        Location location = givenLocation1();

        when(locationRepository.getAllLocationsToSyncAirQualityToday(any(), any()))
                .thenReturn(List.of(location));

        when(airQualityProvider.getCurrentAirQuality(anyDouble(), anyDouble()))
                .thenReturn(givenAirQuality1());

        service.initializeQueue();

        // WHEN
        service.syncTodayAirQuality();
        service.syncTodayAirQuality();

        // THEN
        verify(airQualityProvider, times(1)).getCurrentAirQuality(anyDouble(), anyDouble());
    }
}
