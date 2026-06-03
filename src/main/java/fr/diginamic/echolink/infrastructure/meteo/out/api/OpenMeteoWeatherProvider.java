package fr.diginamic.echolink.infrastructure.meteo.out.api;

import fr.diginamic.echolink.application.meteo.port.out.MeteoProvider;
import fr.diginamic.echolink.domain.meteo.Meteo;
import fr.diginamic.echolink.infrastructure.meteo.out.dto.OpenMeteoResponse;
import fr.diginamic.echolink.infrastructure.meteo.out.mapper.OpenMeteoResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class OpenMeteoWeatherProvider implements MeteoProvider {

    private final RestClient openMeteoRestClient;
    private final OpenMeteoResponseMapper mapper;

    public Meteo getCurrentWeather(double latitude, double longitude) {

        OpenMeteoResponse response = openMeteoRestClient.get()
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
                .body(OpenMeteoResponse.class);

        if (response == null) {
            return null;
        }

        return mapper.toMeteo(response);
    }
}
