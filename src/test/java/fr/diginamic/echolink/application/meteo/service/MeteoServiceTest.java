package fr.diginamic.echolink.application.meteo.service;

import fr.diginamic.echolink.application.location.port.in.LocationGetUseCase;
import fr.diginamic.echolink.application.meteo.port.out.MeteoRepository;
import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;
import fr.diginamic.echolink.domain.meteo.Meteo;
import fr.diginamic.echolink.domain.meteo.exception.MeteoNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static fr.diginamic.echolink.domain.meteo.MeteoTestData.givenMeteo1;
import static fr.diginamic.echolink.domain.meteo.MeteoTestData.givenMeteo3;
import static fr.diginamic.echolink.domain.shared.SharedTestData.givenUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeteoServiceTest {

    @Mock
    private LocationGetUseCase locationGetUseCase;
    @Mock
    private MeteoRepository repository;
    @InjectMocks
    private MeteoService service;

    @Test
    void should_return_last_meteo_for_location() throws LocationNotFoundException, MeteoNotFoundException {
        // GIVEN
        UUID locationId = givenUUID();
        Meteo meteo = givenMeteo1();

        when(locationGetUseCase.existsById(locationId))
                .thenReturn(true);

        when(repository.getLastByLocationId(locationId))
                .thenReturn(Optional.of(meteo));

        // WHEN
        Meteo result = service.getLastByLocationId(locationId);

        // THEN
        assertThat(result)
                .extracting(
                        Meteo::getId,
                        Meteo::getWeatherCondition,
                        Meteo::getTemperature,
                        Meteo::getHumidity
                )
                .containsExactly(
                        meteo.getId(),
                        meteo.getWeatherCondition(),
                        meteo.getTemperature(),
                        meteo.getHumidity()
                );

        verify(locationGetUseCase).existsById(locationId);
        verify(repository).getLastByLocationId(locationId);
    }

    @Test
    void should_throw_location_not_found_exception_when_location_does_not_exist_for_last_meteo() {
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
    void should_throw_meteo_not_found_exception_when_no_meteo_exists_for_location() {
        // GIVEN
        UUID locationId = givenUUID();

        when(locationGetUseCase.existsById(locationId))
                .thenReturn(true);

        when(repository.getLastByLocationId(locationId))
                .thenReturn(Optional.empty());

        // WHEN / THEN
        assertThatThrownBy(() -> service.getLastByLocationId(locationId))
                .isInstanceOf(MeteoNotFoundException.class)
                .hasMessage("No weather data found for location with id " + locationId);

        verify(locationGetUseCase).existsById(locationId);
        verify(repository).getLastByLocationId(locationId);
    }

    @Test
    void should_return_all_meteo_for_location() throws LocationNotFoundException, MeteoNotFoundException {
        // GIVEN
        UUID locationId = givenUUID();

        List<Meteo> meteos = List.of(
                givenMeteo1(),
                givenMeteo3()
        );

        when(locationGetUseCase.existsById(locationId))
                .thenReturn(true);

        when(repository.getAllByLocationId(locationId, 50))
                .thenReturn(meteos);

        // WHEN
        List<Meteo> result = service.getAllByLocationId(locationId);

        // THEN
        assertThat(result).isNotNull().hasSize(2);

        Meteo firstExpected = meteos.getFirst();
        Meteo firstActual = result.getFirst();
        assertThat(firstActual.getId()).isEqualTo(firstExpected.getId());
        assertThat(firstActual.getWeatherCondition()).isEqualTo(firstExpected.getWeatherCondition());
        assertThat(firstActual.getTemperature()).isEqualTo(firstExpected.getTemperature());
        assertThat(firstActual.getHumidity()).isEqualTo(firstExpected.getHumidity());
        assertThat(firstActual.getAtmPressure()).isEqualTo(firstExpected.getAtmPressure());

        Meteo secondExpected = meteos.get(1);
        Meteo secondActual = result.get(1);
        assertThat(secondActual.getId()).isEqualTo(secondExpected.getId());
        assertThat(secondActual.getWeatherCondition()).isEqualTo(secondExpected.getWeatherCondition());
        assertThat(secondActual.getTemperature()).isEqualTo(secondExpected.getTemperature());
        assertThat(secondActual.getHumidity()).isEqualTo(secondExpected.getHumidity());
        assertThat(secondActual.getAtmPressure()).isEqualTo(secondExpected.getAtmPressure());

        verify(locationGetUseCase).existsById(locationId);
        verify(repository).getAllByLocationId(locationId, 50);
    }

    @Test
    void should_throw_location_not_found_exception_when_getting_all_meteo_for_unknown_location() {
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
    void should_throw_exception_when_no_meteo_data_found() {
        //GIVEN
        UUID locationId = givenUUID();

        when(locationGetUseCase.existsById(locationId))
                .thenReturn(true);

        when(repository.getAllByLocationId(locationId, 50))
                .thenReturn(List.of());

        // WHEN / THEN
        assertThatThrownBy(() -> service.getAllByLocationId(locationId))
                .isInstanceOf(MeteoNotFoundException.class)
                .hasMessage("No weather data found for location with id " + locationId);

        verify(locationGetUseCase).existsById(locationId);
        verify(repository).getAllByLocationId(locationId, 50);
    }
}
