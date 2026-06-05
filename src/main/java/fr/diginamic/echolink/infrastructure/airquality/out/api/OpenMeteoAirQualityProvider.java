package fr.diginamic.echolink.infrastructure.airquality.out.api;

import fr.diginamic.echolink.application.airquality.port.out.AirQualityProvider;
import fr.diginamic.echolink.domain.airquality.AirQuality;
import fr.diginamic.echolink.domain.airquality.exception.AirQualityApiSyncException;
import fr.diginamic.echolink.infrastructure.airquality.out.dto.OpenMeteoAirQualityResponse;
import fr.diginamic.echolink.infrastructure.airquality.out.mapper.OpenMeteoAirQualityResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

/**
 * Implementation of {@link AirQualityProvider} using the Open-Meteo Air Quality API.
 * Retrieves current air quality data and maps the API response into domain objects.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OpenMeteoAirQualityProvider implements AirQualityProvider {

    /**
     * REST client used to communicate with the Open-Meteo Air Quality API.
     */
    private final RestClient openMeteoAirQualityRestClient;

    /**
     * Mapper used to convert API responses into domain objects.
     */
    private final OpenMeteoAirQualityResponseMapper mapper;

    @Override
    public AirQuality getCurrentAirQuality(double latitude, double longitude) {

        try {

            OpenMeteoAirQualityResponse response = openMeteoAirQualityRestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/air-quality")
                            .queryParam("latitude", latitude)
                            .queryParam("longitude", longitude)
                            .queryParam(
                                    "hourly",
                                    "nitrogen_dioxide," +
                                            "sulphur_dioxide," +
                                            "ozone," +
                                            "dust," +
                                            "pm10," +
                                            "pm2_5," +
                                            "carbon_monoxide," +
                                            "european_aqi"
                            )
                            .queryParam("forecast_days", 1)
                            .queryParam("timezone", "Europe/Paris")
                            .build())
                    .retrieve()
                    .body(OpenMeteoAirQualityResponse.class);

            if (response == null) {
                throw new AirQualityApiSyncException("OpenMeteo returned null air quality response");
            }

            return mapper.toAirQuality(response);

        } catch (RestClientException ex) {
            throw new AirQualityApiSyncException("Failed to fetch air quality from OpenMeteo", ex);
        }
    }
}
