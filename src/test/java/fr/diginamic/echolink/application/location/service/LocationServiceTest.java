package fr.diginamic.echolink.application.location.service;

import fr.diginamic.echolink.application.location.port.out.LocationRepository;
import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static fr.diginamic.echolink.domain.location.LocationTestData.givenLocation1;
import static fr.diginamic.echolink.domain.location.LocationTestData.givenLocation2;
import static fr.diginamic.echolink.domain.shared.SharedTestData.givenUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;
    @InjectMocks
    private LocationService locationService;

    // ---------------- getById ----------------

    @Test
    void should_return_location_when_id_exists() throws LocationNotFoundException {
        // GIVEN
        Location location = givenLocation1();
        UUID id = givenUUID();

        when(locationRepository.getById(id)).thenReturn(Optional.of(location));

        // WHEN
        Location result = locationService.getById(id);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(location.getName());

        verify(locationRepository).getById(id);
    }

    @Test
    void should_throw_exception_when_location_not_found() {
        // GIVEN
        UUID id = givenUUID();

        when(locationRepository.getById(id)).thenReturn(Optional.empty());

        // WHEN / THEN
        assertThatThrownBy(() -> locationService.getById(id))
                .isInstanceOf(LocationNotFoundException.class)
                .hasMessage("Location with id " + id + " not found");

        verify(locationRepository).getById(id);
    }

    // ---------------- getAllByNameContaining ----------------

    @Test
    void should_return_locations_filtered_by_name() {
        // GIVEN
        List<Location> locations = List.of(
                givenLocation1(),
                givenLocation2()
        );

        when(locationRepository.getAllByNameContaining("Saint")).thenReturn(List.of((locations.getFirst())));

        // WHEN
        List<Location> result = locationService.getAllByNameContaining("Saint");

        // THEN
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).contains("Saint");

        verify(locationRepository).getAllByNameContaining("Saint");
    }

    // ---------------- getAllLocations ----------------

    @Test
    void should_return_all_locations() {
        // GIVEN
        List<Location> locations = List.of(
                givenLocation1(),
                givenLocation2()
        );

        when(locationRepository.getAllLocations()).thenReturn(locations);

        // WHEN
        List<Location> result = locationService.getAllLocations();

        // THEN
        assertThat(result).isEqualTo(locations);

        verify(locationRepository).getAllLocations();
    }

    // ---------------- getAllByGeolocalizationBetween ----------------

    @Test
    void should_call_repository_with_correct_geolocation_bounds_using_real_calculation() {
        // GIVEN
        double latitude = 45.0;
        double longitude = 4.0;
        int delta = 10;

        List<Location> expected = List.of(givenLocation1());

        when(locationRepository.getByGeolocalizationBetween(
                anyDouble(),
                anyDouble(),
                anyDouble(),
                anyDouble(),
                anyInt()
        )).thenReturn(expected);

        // WHEN
        List<Location> result = locationService.getAllByGeolocalizationBetween(latitude, longitude, delta);

        // THEN
        assertThat(result).isEqualTo(expected);

        ArgumentCaptor<Double> latMinCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> latMaxCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> lonMinCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> lonMaxCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Integer> limitCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(locationRepository).getByGeolocalizationBetween(
                latMinCaptor.capture(),
                latMaxCaptor.capture(),
                lonMinCaptor.capture(),
                lonMaxCaptor.capture(),
                limitCaptor.capture()
        );

        double latMin = latMinCaptor.getValue();
        double latMax = latMaxCaptor.getValue();
        double lonMin = lonMinCaptor.getValue();
        double lonMax = lonMaxCaptor.getValue();
        int limit = limitCaptor.getValue();

        // EXPECTED CALCULATIONS
        double DEGREE_TO_KM = 111.11;

        double expectedLatDelta = delta / DEGREE_TO_KM;

        double expectedLatitudeMin = latitude - expectedLatDelta;
        double expectedLatitudeMax = latitude + expectedLatDelta;

        double latitudeRad = Math.toRadians(latitude);
        double latitudeCoefficient = Math.cos(latitudeRad);

        double expectedLongitudeDelta = delta / (DEGREE_TO_KM * latitudeCoefficient);

        double expectedLongitudeMin = longitude - expectedLongitudeDelta;
        double expectedLongitudeMax = longitude + expectedLongitudeDelta;

        // ASSERTIONS
        assertThat(latMin).isEqualTo(expectedLatitudeMin);
        assertThat(latMax).isEqualTo(expectedLatitudeMax);
        assertThat(lonMin).isEqualTo(expectedLongitudeMin);
        assertThat(lonMax).isEqualTo(expectedLongitudeMax);
        assertThat(limit).isEqualTo(10);
    }
}
