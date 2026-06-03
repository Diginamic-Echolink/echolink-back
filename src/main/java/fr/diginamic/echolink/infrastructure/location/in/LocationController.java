package fr.diginamic.echolink.infrastructure.location.in;

import fr.diginamic.echolink.application.location.service.LocationGetService;
import fr.diginamic.echolink.domain.location.Location;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/location", produces = MediaType.APPLICATION_JSON_VALUE)
public class LocationController {

    private final LocationGetService locationGetService;
    private final LocationQueryMapper locationQueryMapper;

    //@Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{locationId}")
    public ResponseEntity<LocationQuery> getLocationById(@PathVariable UUID locationId) {

        LocationQuery locationQuery = locationQueryMapper.toQuery(locationGetService.getById(locationId));
        return ResponseEntity.ok(locationQuery);
    }

    //@Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/geo/{latitude}/{longitude}/delta")
    public ResponseEntity<List<LocationQuery>> getLocationByGeo(@PathVariable float latitude, @PathVariable float longitude, @RequestParam int delta) {

        List<Location> locations = locationGetService.getByGeo(latitude, longitude, delta);
        List<LocationQuery> locationQuery =locations.stream().map(locationQueryMapper::toQuery).toList();

        System.out.println("Je suis passé ici");
        return ResponseEntity.ok(locationQuery);
    }
}
