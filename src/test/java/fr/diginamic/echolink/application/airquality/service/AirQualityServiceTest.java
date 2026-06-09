package fr.diginamic.echolink.application.airquality.service;

import fr.diginamic.echolink.application.airquality.port.out.AirQualityRepository;
import fr.diginamic.echolink.application.location.port.in.LocationGetUseCase;
import fr.diginamic.echolink.domain.airquality.AirQuality;
import fr.diginamic.echolink.domain.airquality.exception.AirQualityNotFoundException;
import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static fr.diginamic.echolink.domain.airquality.AirQualityTestData.givenAirQuality1;
import static fr.diginamic.echolink.domain.airquality.AirQualityTestData.givenAirQuality3;
import static fr.diginamic.echolink.domain.shared.SharedTestData.givenUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AirQualityServiceTest {

    @Mock
    private LocationGetUseCase locationGetUseCase;
    @Mock
    private AirQualityRepository repository;
    @InjectMocks
    private AirQualityService service;

    @Test
    void should_return_air_quality_for_location() throws LocationNotFoundException, AirQualityNotFoundException {
        // GIVEN
        UUID locationId = givenUUID();
        AirQuality airQuality = givenAirQuality1();

        when(locationGetUseCase.existsById(locationId))
                .thenReturn(true);

        when(repository.getLastByLocationId(locationId))
                .thenReturn(java.util.Optional.of(airQuality));

        // WHEN
        AirQuality result = service.getLastByLocationId(locationId);

        // THEN
        assertThat(result)
                .extracting(
                        AirQuality::getId,
                        AirQuality::getParticles10,
                        AirQuality::getParticles25,
                        AirQuality::getCarbonMonoxide
                )
                .containsExactly(
                        airQuality.getId(),
                        airQuality.getParticles10(),
                        airQuality.getParticles25(),
                        airQuality.getCarbonMonoxide()
                );

        verify(locationGetUseCase).existsById(locationId);
        verify(repository).getLastByLocationId(locationId);
    }

    @Test
    void should_throw_location_not_found_exception_when_location_does_not_exist() {
        // GIVEN
        UUID locationId = givenUUID();

        when(locationGetUseCase.existsById(locationId))
                .thenReturn(false);

        // WHEN / THEN
        assertThatThrownBy(() -> service.getLastByLocationId(locationId))
                .isInstanceOf(LocationNotFoundException.class)
                .hasMessage("Location with id " + locationId + " doesn't exist");

        verify(locationGetUseCase).existsById(locationId);
        verifyNoInteractions(repository);
    }

    @Test
    void should_throw_air_quality_not_found_exception_when_no_air_quality_data_exists_for_location() {
        // GIVEN
        UUID locationId = givenUUID();

        when(locationGetUseCase.existsById(locationId))
                .thenReturn(true);

        when(repository.getLastByLocationId(locationId))
                .thenReturn(Optional.empty());

        // WHEN / THEN
        assertThatThrownBy(() -> service.getLastByLocationId(locationId))
                .isInstanceOf(AirQualityNotFoundException.class)
                .hasMessage("No air quality data found for location with id " + locationId);

        verify(locationGetUseCase).existsById(locationId);
        verify(repository).getLastByLocationId(locationId);
    }

    @Test
    void should_return_all_air_quality_for_location() throws LocationNotFoundException, AirQualityNotFoundException {
        // GIVEN
        UUID locationId = givenUUID();

        List<AirQuality> airQualities = List.of(
                givenAirQuality1(),
                givenAirQuality3()
        );

        when(locationGetUseCase.existsById(locationId))
                .thenReturn(true);

        when(repository.getAllByLocationId(locationId, 50))
                .thenReturn(airQualities);

        // WHEN
        List<AirQuality> result = service.getAllByLocationId(locationId);

        // THEN
        assertThat(result).isNotNull().hasSize(2);

        AirQuality firstExpected = airQualities.getFirst();
        AirQuality firstActual = result.getFirst();

        assertThat(firstActual.getId()).isEqualTo(firstExpected.getId());
        assertThat(firstActual.getParticles10()).isEqualTo(firstExpected.getParticles10());
        assertThat(firstActual.getParticles25()).isEqualTo(firstExpected.getParticles25());
        assertThat(firstActual.getCarbonMonoxide()).isEqualTo(firstExpected.getCarbonMonoxide());

        AirQuality secondExpected = airQualities.get(1);
        AirQuality secondActual = result.get(1);

        assertThat(secondActual.getId()).isEqualTo(secondExpected.getId());
        assertThat(secondActual.getParticles10()).isEqualTo(secondExpected.getParticles10());
        assertThat(secondActual.getParticles25()).isEqualTo(secondExpected.getParticles25());
        assertThat(secondActual.getCarbonMonoxide()).isEqualTo(secondExpected.getCarbonMonoxide());

        verify(locationGetUseCase).existsById(locationId);
        verify(repository).getAllByLocationId(locationId, 50);
    }

    @Test
    void should_throw_location_not_found_exception_when_get_all_for_unknown_location() {
        // GIVEN
        UUID locationId = givenUUID();

        when(locationGetUseCase.existsById(locationId))
                .thenReturn(false);

        // WHEN / THEN
        assertThatThrownBy(() -> service.getAllByLocationId(locationId))
                .isInstanceOf(LocationNotFoundException.class)
                .hasMessage("Location with id " + locationId + " doesn't exist");

        verify(locationGetUseCase).existsById(locationId);
        verifyNoInteractions(repository);
    }

    @Test
    void should_throw_air_quality_not_found_exception_when_no_data_found() {
        // GIVEN
        UUID locationId = givenUUID();

        when(locationGetUseCase.existsById(locationId))
                .thenReturn(true);

        when(repository.getAllByLocationId(locationId, 50))
                .thenReturn(List.of());

        // WHEN / THEN
        assertThatThrownBy(() -> service.getAllByLocationId(locationId))
                .isInstanceOf(AirQualityNotFoundException.class)
                .hasMessage("No air quality data found for location with id " + locationId);

        verify(locationGetUseCase).existsById(locationId);
        verify(repository).getAllByLocationId(locationId, 50);
    }
}
