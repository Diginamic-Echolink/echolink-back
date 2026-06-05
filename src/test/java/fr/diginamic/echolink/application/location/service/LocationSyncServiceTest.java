package fr.diginamic.echolink.application.location.service;

import fr.diginamic.echolink.application.location.port.out.LocationProvider;
import fr.diginamic.echolink.application.location.port.out.LocationRepository;
import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.domain.location.exception.LocationApiSyncException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static fr.diginamic.echolink.domain.location.LocationTestData.givenLocation1;
import static fr.diginamic.echolink.domain.location.LocationTestData.givenLocation2;
import static fr.diginamic.echolink.domain.location.LocationTestData.givenLocation3;
import static fr.diginamic.echolink.domain.location.LocationTestData.givenLocation4;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationSyncServiceTest {

    @Mock
    private LocationRepository repository;
    @Mock
    private LocationProvider provider;
    @InjectMocks
    private LocationSyncService service;

    @Test
    void should_sync_only_valid_and_new_locations() {
        // GIVEN
        Location existing = givenLocation1();
        Location newValid = givenLocation2();

        when(repository.getAllInseeCodes()).thenReturn(Set.of(existing.getInseeCode()));
        when(provider.getAllLocations()).thenReturn(List.of(existing, newValid));

        // WHEN
        service.syncLocations();

        // THEN
        verify(repository).getAllInseeCodes();
        verify(provider).getAllLocations();

        verify(repository).saveAll(List.of(newValid));
    }

    @Test
    void should_stop_sync_when_provider_throws_exception() {
        // GIVEN
        when(repository.getAllInseeCodes()).thenReturn(Set.of("12345"));
        when(provider.getAllLocations()).thenThrow(new LocationApiSyncException("API down"));

        // WHEN
        service.syncLocations();

        // THEN
        verify(provider).getAllLocations();
        verify(repository, never()).saveAll(any());
    }

    @Test
    void should_filter_out_locations_with_low_population() {
        // GIVEN
        Location smallCity = givenLocation1();
        smallCity.setPopulation(5_000L);

        Location bigCity = givenLocation2();
        bigCity.setPopulation(50_000L);

        when(repository.getAllInseeCodes()).thenReturn(Set.of());
        when(provider.getAllLocations()).thenReturn(List.of(smallCity, bigCity));

        // WHEN
        service.syncLocations();

        // THEN
        verify(repository).saveAll(List.of(bigCity));
    }

    @Test
    void should_ignore_locations_with_null_insee_code() {
        // GIVEN
        Location invalid = givenLocation1();
        invalid.setInseeCode(null);

        when(repository.getAllInseeCodes()).thenReturn(Set.of());
        when(provider.getAllLocations()).thenReturn(List.of(invalid));

        // WHEN
        service.syncLocations();

        // THEN
        verify(repository).saveAll(List.of());
    }

    @Test
    void should_apply_all_filters_together() {
        // GIVEN
        Location invalidInsee = givenLocation1();
        invalidInsee.setInseeCode(null);

        Location lowPopulation = givenLocation2();
        lowPopulation.setPopulation(1000L);

        Location alreadyExists = givenLocation3();
        alreadyExists.setInseeCode("EXISTING");

        Location valid = givenLocation4();
        valid.setInseeCode("NEW_INSEE");
        valid.setPopulation(50_000L);

        when(repository.getAllInseeCodes()).thenReturn(Set.of("EXISTING"));
        when(provider.getAllLocations()).thenReturn(List.of(invalidInsee, lowPopulation, alreadyExists, valid));

        // WHEN
        service.syncLocations();

        // THEN
        verify(repository).saveAll(List.of(valid));
    }
}
