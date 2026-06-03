package fr.diginamic.echolink.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${spring.geo-api.base-url}")
    private String baseUrlGeoApi;
    @Value("${spring.open-meteo.base-url}")
    private String baseUrlOpenMeteo;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .requestFactory(clientHttpRequestFactory())
                .build();
    }

    private SimpleClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        return factory;
    }

    @Bean(name = "geoApiRestClient")
    public RestClient geoApiRestClient() {
        return RestClient.builder()
                .baseUrl(baseUrlGeoApi)
                .requestFactory(clientHttpRequestFactory())
                .build();
    }

    @Bean(name = "openMeteoRestClient")
    public RestClient openMeteoRestClient() {
        return RestClient.builder()
                .baseUrl(baseUrlOpenMeteo)
                .requestFactory(clientHttpRequestFactory())
                .build();
    }

}
