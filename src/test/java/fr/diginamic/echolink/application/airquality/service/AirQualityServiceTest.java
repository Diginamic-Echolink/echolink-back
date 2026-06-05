package fr.diginamic.echolink.application.airquality.service;

import fr.diginamic.echolink.application.airquality.port.out.AirQualityRepository;
import fr.diginamic.echolink.domain.airquality.AirQuality;
import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static fr.diginamic.echolink.domain.airquality.AirQualityTestData.givenAirQuality1;
import static fr.diginamic.echolink.domain.shared.SharedTestData.givenUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AirQualityServiceTest {

    @Mock
    private AirQualityRepository repository;
    @InjectMocks
    private AirQualityService service;

    @Test
    void should_return_air_quality_when_location_exists() throws LocationNotFoundException {
        // GIVEN
        UUID locationId = givenUUID();

        AirQuality airQuality = givenAirQuality1();

        when(repository.getByLocationId(locationId))
                .thenReturn(Optional.of(airQuality));

        // WHEN
        AirQuality result = service.getByLocationId(locationId);

        // THEN
        assertThat(result).isEqualTo(airQuality);
        verify(repository).getByLocationId(locationId);
    }

    @Test
    void should_throw_exception_when_air_quality_not_found() {
        // GIVEN
        UUID locationId = givenUUID();

        when(repository.getByLocationId(locationId))
                .thenReturn(Optional.empty());

        // WHEN / THEN
        assertThatThrownBy(() -> service.getByLocationId(locationId))
                .isInstanceOf(LocationNotFoundException.class)
                .hasMessage("Location with id " + locationId + " not found");

        verify(repository).getByLocationId(locationId);
    }
}
