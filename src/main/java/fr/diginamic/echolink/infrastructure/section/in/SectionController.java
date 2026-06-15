package fr.diginamic.echolink.infrastructure.section.in;

import fr.diginamic.echolink.application.section.port.in.SectionDeleteUseCase;
import fr.diginamic.echolink.application.section.port.in.SectionGetUseCase;
import fr.diginamic.echolink.application.section.port.in.SectionCreateUseCase;
import fr.diginamic.echolink.application.section.port.in.SectionUpdateUseCase;
import fr.diginamic.echolink.domain.section.Section;
import fr.diginamic.echolink.domain.section.SectionUpsertRequest;
import fr.diginamic.echolink.domain.section.exception.SectionNotFoundException;
import fr.diginamic.echolink.infrastructure.common.in.dto.ErrorMessageQuery;
import fr.diginamic.echolink.infrastructure.common.in.dto.MessageResponse;
import fr.diginamic.echolink.infrastructure.section.in.dto.SectionQuery;
import fr.diginamic.echolink.infrastructure.section.in.mapper.SectionQueryMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * REST controller exposing section management endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/section", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Section", description = "Section management")
public class SectionController {

    /**
     * Use case responsible for retrieving sections.
     */
    private final SectionGetUseCase getUseCase;

    /**
     * Use case responsible for creating sections.
     */
    private final SectionCreateUseCase postUseCase;

    /**
     * Use case responsible for updating sections.
     */
    private final SectionUpdateUseCase updateUseCase;

    /**
     * Use case responsible for deleting sections.
     */
    private final SectionDeleteUseCase deleteUseCase;

    /**
     * Mapper used to convert section domain objects into query DTOs.
     */
    private final SectionQueryMapper mapper;

    /**
     * Retrieves a section by its unique identifier.
     *
     * @param sectionId unique identifier of the section to retrieve
     * @return section information corresponding to the given identifier
     * @throws SectionNotFoundException if no section is found with the specified identifier
     */
    @GetMapping("/{sectionId}")
    @RolesAllowed({"ADMIN", "USER"})
    @Operation(
            operationId = "getSectionById",
            summary = "Get a section by ID",
            description = "Returns a section based on its unique identifier",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Section retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SectionQuery.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Section not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageQuery.class)
                            )
                    )
            }
    )
    public ResponseEntity<SectionQuery> getSectionById(
            @Parameter(
                    description = "Section UUID",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID sectionId
    ) throws SectionNotFoundException {

        Section section = getUseCase.getById(sectionId);
        SectionQuery query = mapper.toQuery(section);

        return ResponseEntity.ok(query);
    }

    /**
     * Retrieves all available sections.
     *
     * @return list of section information
     */
    @GetMapping("/all")
    @RolesAllowed({"ADMIN", "USER"})
    @Operation(
            operationId = "getAllSections",
            summary = "Get all sections",
            description = "Returns all available sections",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sections retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SectionQuery.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            }
    )
    public ResponseEntity<List<SectionQuery>> getAllSections() {

        List<Section> sections = getUseCase.getAllSections();
        List<SectionQuery> query = sections.stream().map(mapper::toQuery).toList();

        return ResponseEntity.ok(query);
    }

    /**
     * Creates a new section.
     *
     * @param request request containing section information
     * @return created section information
     */
    @PostMapping
    @RolesAllowed({"ADMIN"})
    @Operation(
            operationId = "createSection",
            summary = "Create a section",
            description = "Creates a new section",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Section created successfully",
                            content = @Content(schema = @Schema(implementation = SectionQuery.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request",
                            content = @Content(schema = @Schema(implementation = ErrorMessageQuery.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            }
    )
    public ResponseEntity<SectionQuery> createSection(@Valid @RequestBody SectionUpsertRequest request) {

        Section section = postUseCase.create(request);
        SectionQuery query = mapper.toQuery(section);

        return ResponseEntity.status(CREATED).body(query);
    }

    /**
     * Updates an existing section.
     *
     * @param sectionId unique identifier of the section to update
     * @param request request containing updated section information
     * @return updated section information
     * @throws SectionNotFoundException if no section is found with the specified identifier
     */
    @PutMapping("/{sectionId}")
    @RolesAllowed({"ADMIN"})
    @Operation(
            operationId = "updateSection",
            summary = "Update a section",
            description = "Updates an existing section",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Section updated successfully",
                            content = @Content(schema = @Schema(implementation = SectionQuery.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Section not found",
                            content = @Content(schema = @Schema(implementation = ErrorMessageQuery.class))
                    )
            }
    )
    public ResponseEntity<SectionQuery> updateSection(
            @Parameter(description = "Section UUID", required = true) @PathVariable UUID sectionId,
            @Valid @RequestBody SectionUpsertRequest request
    ) throws SectionNotFoundException {

        Section section = updateUseCase.update(sectionId, request);
        SectionQuery query = mapper.toQuery(section);

        return ResponseEntity.ok(query);
    }

    /**
     * Deletes a section.
     *
     * @param sectionId unique identifier of the section to delete
     * @return success response
     * @throws SectionNotFoundException if no section is found with the specified identifier
     */
    @DeleteMapping("/{sectionId}")
    @RolesAllowed({"ADMIN"})
    @Operation(
            operationId = "deleteSection",
            summary = "Delete a section",
            description = "Deletes a section by its ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Section deleted successfully",
                            content = @Content(schema = @Schema(implementation = MessageResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Section not found",
                            content = @Content(schema = @Schema(implementation = ErrorMessageQuery.class))
                    )
            }
    )
    public ResponseEntity<MessageResponse> deleteSection(
            @Parameter(description = "Section UUID", required = true) @PathVariable UUID sectionId
    ) throws SectionNotFoundException {

        deleteUseCase.delete(sectionId);

        return ResponseEntity.ok(new MessageResponse("Section with id: " + sectionId + " is correctly deleted"));
    }
}
