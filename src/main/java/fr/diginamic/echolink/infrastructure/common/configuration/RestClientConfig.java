package fr.diginamic.echolink.infrastructure.common.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${spring.geo-api.base-url}")
    private String baseUrlGeoApi;
    @Value("${spring.open-meteo.weather.base-url}")
    private String baseUrlOpenMeteoWeather;
    @Value("${spring.open-meteo.air-quality.base-url}")
    private String baseUrlOpenMeteoAirQuality;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .requestFactory(clientHttpRequestFactory())
                .build();
    }

    private SimpleClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(8000);
        factory.setReadTimeout(20000);
        return factory;
    }

    @Bean(name = "geoApiRestClient")
    public RestClient geoApiRestClient() {
        return RestClient.builder()
                .baseUrl(baseUrlGeoApi)
                .requestFactory(clientHttpRequestFactory())
                .build();
    }

    @Bean(name = "openMeteoWeatherRestClient")
    public RestClient openMeteoRestClient() {
        return RestClient.builder()
                .baseUrl(baseUrlOpenMeteoWeather)
                .requestFactory(clientHttpRequestFactory())
                .build();
    }

    @Bean(name = "openMeteoAirQualityRestClient")
    public RestClient openMeteoAirQualityRestClient() {
        return RestClient.builder()
                .baseUrl(baseUrlOpenMeteoAirQuality)
                .requestFactory(clientHttpRequestFactory())
                .build();
    }

}
