package fr.diginamic.echolink.infrastructure.location.in;

import fr.diginamic.echolink.application.location.port.in.LocationGetUseCase;
import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;
import fr.diginamic.echolink.infrastructure.common.in.dto.ErrorMessageQuery;
import fr.diginamic.echolink.infrastructure.location.in.dto.LocationQuery;
import fr.diginamic.echolink.infrastructure.location.in.mapper.LocationQueryMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@Tag(name = "Location", description = "Location management")
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
    @RolesAllowed({"ADMIN", "USER"})
    @Operation(
            operationId = "getLocationById",
            summary = "Get a location by ID",
            description = "Returns the location corresponding to the given UUID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Location found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LocationQuery.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Location not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageQuery.class)
                            )
                    )
            }
    )
    public ResponseEntity<LocationQuery> getLocationById(
            @Parameter(description = "Location UUID", required = true)
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
    @RolesAllowed({"ADMIN", "USER"})
    @Operation(
            operationId = "searchLocationsByName",
            summary = "Search locations by name",
            description = "Returns all locations whose name contains the provided value",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Locations retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = LocationQuery.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            }
    )
    public ResponseEntity<List<LocationQuery>> getAllLocationsByNameContaining(
            @Parameter(description = "Location name or partial name", required = true, example = "Paris")
            @RequestParam String name
    ) {

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
    @RolesAllowed({"ADMIN", "USER"})
    @Operation(
            operationId = "getAllLocations",
            summary = "Get all locations",
            description = "Returns all available locations",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Locations retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = LocationQuery.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            }
    )
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
    @RolesAllowed({"ADMIN", "USER"})
    @Operation(
            operationId = "getLocationsByGeolocation",
            summary = "Search locations by geographic coordinates",
            description = "Returns all locations located within the specified radius around a geographic point",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Locations retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = LocationQuery.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            }
    )
    public ResponseEntity<List<LocationQuery>> getAllLocationsByGeo(
            @Parameter(description = "Reference latitude", required = true, example = "48.8566")
            @PathVariable double latitude,
            @Parameter(description = "Reference longitude", required = true, example = "2.3522")
            @PathVariable double longitude,
            @Parameter(description = "Search radius in kilometers", required = true, example = "10")
            @PathVariable int delta
    ) {

        List<Location> locations = getUseCase.getAllByGeolocalizationBetween(latitude, longitude, delta);
        List<LocationQuery> query = locations.stream().map(mapper::toQuery).toList();

        return ResponseEntity.ok(query);
    }
}
