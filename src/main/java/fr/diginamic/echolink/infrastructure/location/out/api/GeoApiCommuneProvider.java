package fr.diginamic.echolink.infrastructure.location.out.api;

import fr.diginamic.echolink.application.location.port.out.LocationProvider;
import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.infrastructure.location.out.dto.CommuneDto;
import fr.diginamic.echolink.infrastructure.location.out.mapper.CommuneMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GeoApiCommuneProvider implements LocationProvider {

    private final RestClient geoApiRestClient;
    private final CommuneMapper mapper;

    @Override
    public List<Location> getAllLocations() {

        CommuneDto[] response = geoApiRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/communes")
                        .queryParam("format", "json")
                        .queryParam(
                                "fields",
                                "nom,code,codesPostaux,centre"
                        )
                        .build())
                .header("Accept", "application/json")
                .retrieve()
                .body(CommuneDto[].class);

        if(response == null || response.length == 0) {
            return List.of();
        }

        return Arrays.stream(response).map(mapper::toLocation).toList();
    }

}
