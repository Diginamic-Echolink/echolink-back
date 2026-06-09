package fr.diginamic.echolink.infrastructure.common.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

/**
 * Configuration class responsible for creating and configuring
 * {@link RestClient} instances used to communicate with external APIs.
 */
@Configuration
public class RestClientConfig {

    @Value("${app.integrations.geo-api.base-url}")
    private String baseUrlGeoApi;

    @Value("${app.integrations.open-meteo.weather.base-url}")
    private String baseUrlOpenMeteoWeather;

    @Value("${app.integrations.open-meteo.air-quality.base-url}")
    private String baseUrlOpenMeteoAirQuality;

    /**
     * Creates a generic {@link RestClient}.
     *
     * @return configured REST client
     */
    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .requestFactory(clientHttpRequestFactory())
                .build();
    }

    /**
     * Creates the HTTP request factory used by all REST clients.
     * Configures connection and read timeouts.
     *
     * @return configured request factory
     */
    private SimpleClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(8000);
        factory.setReadTimeout(20000);
        return factory;
    }

    /**
     * Creates a REST client configured for the Geo API.
     *
     * @return Geo API REST client
     */
    @Bean(name = "geoApiRestClient")
    public RestClient geoApiRestClient() {
        return RestClient.builder()
                .baseUrl(baseUrlGeoApi)
                .requestFactory(clientHttpRequestFactory())
                .build();
    }

    /**
     * Creates a REST client configured for the Open-Meteo
     * weather API.
     *
     * @return Open-Meteo weather REST client
     */
    @Bean(name = "openMeteoWeatherRestClient")
    public RestClient openMeteoRestClient() {
        return RestClient.builder()
                .baseUrl(baseUrlOpenMeteoWeather)
                .requestFactory(clientHttpRequestFactory())
                .build();
    }

    /**
     * Creates a REST client configured for the Open-Meteo
     * air quality API.
     *
     * @return Open-Meteo air quality REST client
     */
    @Bean(name = "openMeteoAirQualityRestClient")
    public RestClient openMeteoAirQualityRestClient() {
        return RestClient.builder()
                .baseUrl(baseUrlOpenMeteoAirQuality)
                .requestFactory(clientHttpRequestFactory())
                .build();
    }

}
