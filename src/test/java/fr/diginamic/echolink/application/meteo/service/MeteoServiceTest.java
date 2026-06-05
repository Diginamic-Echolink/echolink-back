package fr.diginamic.echolink.application.meteo.service;

import fr.diginamic.echolink.application.meteo.port.out.MeteoRepository;
import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;
import fr.diginamic.echolink.domain.meteo.Meteo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static fr.diginamic.echolink.domain.meteo.MeteoTestData.givenMeteo1;
import static fr.diginamic.echolink.domain.shared.SharedTestData.givenUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MeteoServiceTest {

    @Mock
    private MeteoRepository repository;
    @InjectMocks
    private MeteoService service;

    @Test
    void should_return_last_meteo_for_location() throws LocationNotFoundException {
        // GIVEN
        UUID locationId = givenUUID();

        Meteo meteo = givenMeteo1();

        when(repository.getLastMeteoByLocationId(locationId))
                .thenReturn(Optional.of(meteo));

        // WHEN
        Meteo result = service.getLastMeteoByLocationId(locationId);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(meteo);

        verify(repository).getLastMeteoByLocationId(locationId);
    }

    @Test
    void should_throw_exception_when_no_last_meteo_found() {
        // GIVEN
        UUID locationId = givenUUID();

        when(repository.getLastMeteoByLocationId(locationId))
                .thenReturn(Optional.empty());

        // WHEN / THEN
        assertThatThrownBy(() -> service.getLastMeteoByLocationId(locationId))
                .isInstanceOf(LocationNotFoundException.class)
                .hasMessage("Location with id " + locationId + " not found");

        verify(repository).getLastMeteoByLocationId(locationId);
    }
}
