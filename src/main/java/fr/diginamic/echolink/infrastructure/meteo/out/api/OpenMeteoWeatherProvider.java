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

@Component
@RequiredArgsConstructor
public class OpenMeteoWeatherProvider implements MeteoProvider {

    private final RestClient openMeteoWeatherRestClient;
    private final OpenMeteoWeatherResponseMapper mapper;

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
