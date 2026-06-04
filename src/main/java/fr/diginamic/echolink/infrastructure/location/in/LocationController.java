package fr.diginamic.echolink.infrastructure.location.in;

import fr.diginamic.echolink.application.location.service.LocationService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/location", produces = MediaType.APPLICATION_JSON_VALUE)
public class LocationController {

    private final LocationService locationService;
    private final LocationQueryMapper locationQueryMapper;

    @GetMapping("/{locationId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<LocationQuery> getLocationById(
            @PathVariable UUID locationId
    ) throws LocationNotFoundException {
        Location location = locationService.getById(locationId);
        LocationQuery query = locationQueryMapper.toQuery(location);
        return ResponseEntity.ok(query);
    }

    @GetMapping("/all")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<LocationQuery>> getLocationById() {
        List<Location> locations = locationService.getAllLocations();
        List<LocationQuery> query = locations.stream().map(locationQueryMapper::toQuery).toList();
        return ResponseEntity.ok(query);
    }

    @GetMapping("/geo/{latitude}/{longitude}/{delta}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<LocationQuery>> getLocationByGeo(
            @PathVariable double latitude,
            @PathVariable double longitude,
            @PathVariable int delta
    ) {
        List<Location> locations = locationService.getByGeo(latitude, longitude, delta);
        List<LocationQuery> query = locations.stream().map(locationQueryMapper::toQuery).toList();
        return ResponseEntity.ok(query);
    }
}
