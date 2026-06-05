package fr.diginamic.echolink.infrastructure.location.out.api;

import fr.diginamic.echolink.application.location.port.out.LocationProvider;
import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.domain.location.exception.LocationApiSyncException;
import fr.diginamic.echolink.infrastructure.location.out.dto.GeoApiCommuneDto;
import fr.diginamic.echolink.infrastructure.location.out.mapper.GeoApiCommuneMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Arrays;
import java.util.List;

/**
 * Implementation of {@link LocationProvider} that retrieves location data
 * from the French Geo API.
 */
@Component
@RequiredArgsConstructor
public class GeoApiCommuneProvider implements LocationProvider {

    /**
     * REST client used to communicate with the Geo API.
     */
    private final RestClient geoApiRestClient;

    /**
     * Mapper used to convert Geo API responses into domain objects.
     */
    private final GeoApiCommuneMapper mapper;

    @Override
    public List<Location> getAllLocations() {

        try {

            GeoApiCommuneDto[] response = geoApiRestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/communes")
                            .queryParam("format", "json")
                            .queryParam(
                                    "fields",
                                    "nom,code,codesPostaux,centre,population"
                            )
                            .build())
                    .header("Accept", "application/json")
                    .retrieve()
                    .body(GeoApiCommuneDto[].class);

            if (response == null || response.length == 0) {
                throw new LocationApiSyncException("Geo API returned null or empty location response");
            }

            return Arrays.stream(response).map(mapper::toLocation).toList();

        }
        catch (RestClientException ex) {
            throw new LocationApiSyncException("Failed to fetch locations from Geo API", ex);
        }
    }
}
