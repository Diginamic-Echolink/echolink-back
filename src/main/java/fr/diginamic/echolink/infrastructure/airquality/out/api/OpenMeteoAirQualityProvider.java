package fr.diginamic.echolink.infrastructure.airquality.out.api;

import fr.diginamic.echolink.application.airquality.port.out.AirQualityProvider;
import fr.diginamic.echolink.domain.airquality.AirQuality;
import fr.diginamic.echolink.infrastructure.airquality.out.dto.OpenMeteoAirQualityResponse;
import fr.diginamic.echolink.infrastructure.airquality.out.mapper.OpenMeteoAirQualityResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class OpenMeteoAirQualityProvider implements AirQualityProvider {

    private final RestClient openMeteoAirQualityRestClient;
    private final OpenMeteoAirQualityResponseMapper mapper;

    @Override
    public AirQuality getCurrentAirQuality(double latitude, double longitude) {

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
            return null;
        }

        return mapper.toAirQuality(response);
    }
}
