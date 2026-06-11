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
import static org.assertj.core.api.Assertions.within;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

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
        assertThat(result.getId()).isEqualTo(location.getId());
        assertThat(result.getName()).isEqualTo(location.getName());
        assertThat(result.getInseeCode()).isEqualTo(location.getInseeCode());
        assertThat(result.getPostalCode()).isEqualTo(location.getPostalCode());
        assertThat(result.getLatitude()).isEqualTo(location.getLatitude());
        assertThat(result.getLongitude()).isEqualTo(location.getLongitude());
        assertThat(result.getPopulation()).isEqualTo(location.getPopulation());

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

        Location firstExpected = locations.getFirst();
        Location actualFirst = result.getFirst();

        assertThat(actualFirst.getName()).isEqualTo(firstExpected.getName());
        assertThat(actualFirst.getInseeCode()).isEqualTo(firstExpected.getInseeCode());
        assertThat(actualFirst.getPostalCode()).isEqualTo(firstExpected.getPostalCode());
        assertThat(actualFirst.getLatitude()).isEqualTo(firstExpected.getLatitude());
        assertThat(actualFirst.getLongitude()).isEqualTo(firstExpected.getLongitude());
        assertThat(actualFirst.getPopulation()).isEqualTo(firstExpected.getPopulation());

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
        assertThat(result).hasSize(2);
        assertThat(result)
                .extracting(Location::getName)
                .containsExactly(
                        locations.get(0).getName(),
                        locations.get(1).getName()
                );
        assertThat(result)
                .extracting(Location::getInseeCode)
                .containsExactly(
                        locations.get(0).getInseeCode(),
                        locations.get(1).getInseeCode()
                );

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
        assertThat(latMin).isCloseTo(expectedLatitudeMin, within(0.000001));
        assertThat(latMax).isCloseTo(expectedLatitudeMax, within(0.000001));
        assertThat(lonMin).isCloseTo(expectedLongitudeMin, within(0.000001));
        assertThat(lonMax).isCloseTo(expectedLongitudeMax, within(0.000001));
        assertThat(limit).isEqualTo(10);

        assertThat(result).hasSize(1);

        Location actual = result.getFirst();
        Location expectedLocation = expected.getFirst();

        assertThat(actual.getName()).isEqualTo(expectedLocation.getName());
        assertThat(actual.getInseeCode()).isEqualTo(expectedLocation.getInseeCode());
        assertThat(actual.getPostalCode()).isEqualTo(expectedLocation.getPostalCode());
        assertThat(actual.getLatitude()).isEqualTo(expectedLocation.getLatitude());
        assertThat(actual.getLongitude()).isEqualTo(expectedLocation.getLongitude());
        assertThat(actual.getPopulation()).isEqualTo(expectedLocation.getPopulation());
    }
}
