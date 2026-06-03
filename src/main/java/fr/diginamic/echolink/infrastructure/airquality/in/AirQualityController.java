package fr.diginamic.echolink.infrastructure.airquality.in;

import fr.diginamic.echolink.application.airquality.port.in.AirQualityGetUseCase;
import fr.diginamic.echolink.domain.airquality.AirQuality;
import fr.diginamic.echolink.infrastructure.airquality.in.dto.AirQualityQuery;
import fr.diginamic.echolink.infrastructure.airquality.in.mapper.AirQualityQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST controller exposing air quality endpoints.
 * Provides access to current and historical air quality data
 * associated with a location.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/air-quality", produces = MediaType.APPLICATION_JSON_VALUE)
public class AirQualityController {

    /**
     * Use case for retrieving air quality data.
     */
    private final AirQualityGetUseCase getUseCase;

    /**
     * Mapper used to convert domain objects into query DTOs.
     */
    private final AirQualityQueryMapper mapper;

    /**
     * Retrieves the latest air quality data for a location.
     *
     * @param locationId unique identifier of the location
     * @return the air quality data associated with the location
     */
    @GetMapping("/{locationId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<AirQualityQuery> getByLocationId(@PathVariable UUID locationId) {
        AirQuality airQualities = getUseCase.getByLocationId(locationId);
        AirQualityQuery query = mapper.toQuery(airQualities);
        return ResponseEntity.ok(query);
    }

    /**
     * Retrieves all available air quality records for a location.
     *
     * @param locationId unique identifier of the location
     * @return a list of air quality records associated with the location
     */
    @GetMapping("/all/{locationId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<AirQualityQuery>> getAllByLocationId(@PathVariable UUID locationId) {
        List<AirQuality> airQualities = getUseCase.getAllByLocationId(locationId);
        List<AirQualityQuery> query = airQualities.stream().map(mapper::toQuery).toList();
        return ResponseEntity.ok(query);
    }
}