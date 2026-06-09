package fr.diginamic.echolink.infrastructure.airquality.in;

import fr.diginamic.echolink.application.airquality.port.in.AirQualityGetUseCase;
import fr.diginamic.echolink.domain.airquality.AirQuality;
import fr.diginamic.echolink.domain.airquality.exception.AirQualityNotFoundException;
import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;
import fr.diginamic.echolink.infrastructure.airquality.in.dto.AirQualityQuery;
import fr.diginamic.echolink.infrastructure.airquality.in.mapper.AirQualityQueryMapper;
import fr.diginamic.echolink.infrastructure.common.in.dto.ErrorMessageQuery;
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
@Tag(name = "Air Quality", description = "Air quality management")
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
    @RolesAllowed({"ADMIN", "USER"})
    @Operation(
            operationId = "getLastAirQualityByLocationId",
            summary = "Get latest air quality for a location",
            description = "Returns the most recent air quality data for the given location ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AirQualityQuery.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Location or Air Quality datas not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageQuery.class)
                            )
                    )
            }
    )
    public ResponseEntity<AirQualityQuery> getLastAirQualityByLocationId(
            @Parameter(description = "Location UUID", required = true) @PathVariable UUID locationId
    ) throws LocationNotFoundException, AirQualityNotFoundException {

        AirQuality airQualities = getUseCase.getLastByLocationId(locationId);
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
    @RolesAllowed({"ADMIN", "USER"})
    @Operation(
            operationId = "getAllAirQualityByLocationId",
            summary = "Get all air quality records for a location",
            description = "Returns the full history of air quality measurements for the given location ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of air quality records retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = AirQualityQuery.class)))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Location or Air Quality datas not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageQuery.class)
                            )
                    )
            }
    )
    public ResponseEntity<List<AirQualityQuery>> getAllAirQualityByLocationId(
            @Parameter(description = "Location UUID", required = true) @PathVariable UUID locationId
    ) throws AirQualityNotFoundException, LocationNotFoundException {

        List<AirQuality> airQualities = getUseCase.getAllByLocationId(locationId);
        List<AirQualityQuery> query = airQualities.stream().map(mapper::toQuery).toList();

        return ResponseEntity.ok(query);
    }
}
