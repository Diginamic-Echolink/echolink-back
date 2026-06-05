package fr.diginamic.echolink.infrastructure.location.in;

import fr.diginamic.echolink.application.location.port.in.LocationGetUseCase;
import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;
import fr.diginamic.echolink.infrastructure.location.in.dto.LocationQuery;
import fr.diginamic.echolink.infrastructure.location.in.mapper.LocationQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST controller exposing location retrieval endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/location", produces = MediaType.APPLICATION_JSON_VALUE)
public class LocationController {

    /**
     * Use case responsible for retrieving locations.
     */
    private final LocationGetUseCase getUseCase;

    /**
     * Mapper used to convert location domain objects into query DTOs.
     */
    private final LocationQueryMapper mapper;

    /**
     * Retrieves a location by its unique identifier.
     *
     * @param locationId unique identifier of the location
     * @return location information
     * @throws LocationNotFoundException if no location is found with the specified identifier
     */
    @GetMapping("/{locationId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<LocationQuery> getLocationById(
            @PathVariable UUID locationId
    ) throws LocationNotFoundException {
        Location location = getUseCase.getById(locationId);
        LocationQuery query = mapper.toQuery(location);
        return ResponseEntity.ok(query);
    }

    /**
     * Retrieves locations whose name contains the specified value.
     *
     * @param name text used to search location names
     * @return list of matching locations
     */
    @GetMapping("/search")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<LocationQuery>> getAllLocationsByNameContaining(@RequestParam String name) {
        List<Location> locations = getUseCase.getAllByNameContaining(name);
        List<LocationQuery> query = locations.stream().map(mapper::toQuery).toList();
        return ResponseEntity.ok(query);
    }

    /**
     * Retrieves all available locations.
     *
     * @return list of location information
     */
    @GetMapping("/all")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<LocationQuery>> getAllLocations() {
        List<Location> locations = getUseCase.getAllLocations();
        List<LocationQuery> query = locations.stream().map(mapper::toQuery).toList();
        return ResponseEntity.ok(query);
    }

    /**
     * Retrieves locations located within a geographic area around the specified coordinates.
     *
     * @param latitude reference latitude
     * @param longitude reference longitude
     * @param delta search radius in kilometers
     * @return list of matching locations
     */
    @GetMapping("/geo/{latitude}/{longitude}/{delta}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<LocationQuery>> getAllLocationsByGeo(
            @PathVariable double latitude,
            @PathVariable double longitude,
            @PathVariable int delta
    ) {
        List<Location> locations = getUseCase.getAllByGeolocalizationBetween(latitude, longitude, delta);
        List<LocationQuery> query = locations.stream().map(mapper::toQuery).toList();
        return ResponseEntity.ok(query);
    }
}
