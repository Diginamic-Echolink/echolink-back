package fr.diginamic.echolink.infrastructure.meteo.out.api;

import fr.diginamic.echolink.application.meteo.port.out.MeteoProvider;
import fr.diginamic.echolink.domain.meteo.Meteo;
import fr.diginamic.echolink.domain.meteo.exception.MeteoApiSyncException;
import fr.diginamic.echolink.infrastructure.meteo.out.dto.OpenMeteoWeatherResponse;
import fr.diginamic.echolink.infrastructure.meteo.out.mapper.OpenMeteoWeatherResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

/**
 * Implementation of {@link MeteoProvider} that retrieves weather data
 * from the Open-Meteo API.
 */
@Component
@RequiredArgsConstructor
public class OpenMeteoWeatherProvider implements MeteoProvider {

    /**
     * REST client used to communicate with the Open-Meteo API.
     */
    private final RestClient openMeteoWeatherRestClient;

    /**
     * Mapper used to convert Open-Meteo responses into domain objects.
     */
    private final OpenMeteoWeatherResponseMapper mapper;

    @Override
    public Meteo getCurrentWeather(double latitude, double longitude) throws MeteoApiSyncException {

        try {

            OpenMeteoWeatherResponse response = openMeteoWeatherRestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/forecast")
                            .queryParam("latitude", latitude)
                            .queryParam("longitude", longitude)
                            .queryParam("daily",
                                    "weather_code,temperature_2m_max,temperature_2m_min,precipitation_sum")
                            .queryParam("hourly",
                                    "relative_humidity_2m,pressure_msl,wind_speed_10m,wind_direction_10m")
                            .queryParam("timezone", "Europe/Paris")
                            .build())
                    .retrieve()
                    .body(OpenMeteoWeatherResponse.class);

            if (response == null) {
                throw new MeteoApiSyncException("OpenMeteo returned null weather response");
            }

            return mapper.toMeteo(response);

        } catch (RestClientException ex) {
            throw new MeteoApiSyncException("Failed to fetch weather from OpenMeteo", ex);
        }
    }
}
