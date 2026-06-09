package fr.diginamic.echolink.infrastructure.meteo.in;

import fr.diginamic.echolink.application.meteo.port.in.MeteoGetUseCase;
import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;
import fr.diginamic.echolink.domain.meteo.Meteo;
import fr.diginamic.echolink.domain.meteo.exception.MeteoNotFoundException;
import fr.diginamic.echolink.infrastructure.common.in.dto.ErrorMessageQuery;
import fr.diginamic.echolink.infrastructure.meteo.in.dto.MeteoQuery;
import fr.diginamic.echolink.infrastructure.meteo.in.mapper.MeteoQueryMapper;
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
 * REST controller exposing weather data endpoints.
 * Provides access to current and historical weather information
 * associated with a location.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/meteo", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Meteo", description = "Weather Data management")
public class MeteoController {

    /**
     * Use case for retrieving weather data.
     */
    private final MeteoGetUseCase getUseCase;

    /**
     * Mapper used to convert domain objects into query DTOs.
     */
    private final MeteoQueryMapper mapper;

    /**
     * Retrieves the latest weather data for a location.
     *
     * @param locationId unique identifier of the location
     * @return the weather data associated with the location
     */
    @GetMapping("/{locationId}")
    @RolesAllowed({"ADMIN", "USER"})
    @Operation(
            operationId = "getLastMeteoByLocationId",
            summary = "Get latest weather data for a location",
            description = "Returns the most recent weather data associated with the given location ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MeteoQuery.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Location or weather data not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageQuery.class)
                            )
                    )
            }
    )
    public ResponseEntity<MeteoQuery> getLastMeteoByLocationId(
            @Parameter(description = "Location UUID", required = true) @PathVariable UUID locationId
    ) throws LocationNotFoundException, MeteoNotFoundException {

        Meteo meteo = getUseCase.getLastByLocationId(locationId);
        MeteoQuery query = mapper.toQuery(meteo);

        return ResponseEntity.ok(query);
    }

    /**
     * Retrieves all available weather records for a location.
     *
     * @param locationId unique identifier of the location
     * @return a list of weather records associated with the location
     */
    @GetMapping("/all/{locationId}")
    @RolesAllowed({"ADMIN", "USER"})
    @Operation(
            operationId = "getAllMeteosByLocationId",
            summary = "Get all weather records for a location",
            description = "Returns the full history of weather data for the given location ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of weather records retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = MeteoQuery.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Location or weather data not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageQuery.class)
                            )
                    )
            }
    )
    public ResponseEntity<List<MeteoQuery>> getAllMeteosByLocationId(
            @Parameter(description = "Location UUID", required = true) @PathVariable UUID locationId
    ) throws LocationNotFoundException, MeteoNotFoundException {

        List<Meteo> meteos = getUseCase.getAllByLocationId(locationId);
        List<MeteoQuery> query = meteos.stream().map(mapper::toQuery).toList();

        return ResponseEntity.ok(query);
    }
}
