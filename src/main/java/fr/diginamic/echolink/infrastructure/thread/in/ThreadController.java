package fr.diginamic.echolink.infrastructure.thread.in;

import fr.diginamic.echolink.application.profile.port.in.ProfileGetUseCase;
import fr.diginamic.echolink.application.thread.port.in.ThreadCreateUseCase;
import fr.diginamic.echolink.application.thread.port.in.ThreadDeleteUseCase;
import fr.diginamic.echolink.application.thread.port.in.ThreadGetUseCase;
import fr.diginamic.echolink.application.thread.port.in.ThreadUpdateUseCase;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotAllowedException;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;
import fr.diginamic.echolink.domain.section.exception.SectionNotFoundException;
import fr.diginamic.echolink.domain.thread.Thread;
import fr.diginamic.echolink.domain.thread.ThreadCreateRequest;
import fr.diginamic.echolink.domain.thread.ThreadUpdateRequest;
import fr.diginamic.echolink.domain.thread.exception.ThreadNotFoundException;
import fr.diginamic.echolink.infrastructure.common.in.dto.ErrorMessageQuery;
import fr.diginamic.echolink.infrastructure.common.in.dto.MessageResponse;
import fr.diginamic.echolink.infrastructure.thread.in.dto.ThreadQuery;
import fr.diginamic.echolink.infrastructure.thread.in.mapper.ThreadQueryMapper;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * REST controller exposing thread management endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/thread", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Thread", description = "Thread management")
public class ThreadController {

    /**
     * Use case responsible for retrieving threads.
     */
    private final ThreadGetUseCase getUseCase;

    /**
     * Use case responsible for creating threads.
     */
    private final ThreadCreateUseCase postUseCase;

    /**
     * Use case responsible for updating threads.
     */
    private final ThreadUpdateUseCase updateUseCase;

    /**
     * Use case responsible for deleting threads.
     */
    private final ThreadDeleteUseCase deleteUseCase;

    /**
     * Use case responsible for retrieving threads.
     */
    private final ProfileGetUseCase profileGetUseCase;

    /**
     * Mapper used to convert thread domain objects into query DTOs.
     */
    private final ThreadQueryMapper mapper;

    /**
     * Retrieves all threads associated with a section.
     *
     * @param sectionId unique identifier of the section
     * @return list of thread information
     */
    @GetMapping("/all/{sectionId}")
    @RolesAllowed({"ADMIN", "USER"})
    @Operation(
            operationId = "getThreadsBySectionId",
            summary = "Get all threads by section",
            description = "Returns all threads belonging to a specific section",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Threads retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ThreadQuery.class))
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
                            content = @Content(schema = @Schema(implementation = ErrorMessageQuery.class))
                    )
            }
    )
    public ResponseEntity<List<ThreadQuery>> getAllBySectionId(
            @Parameter(description = "Section UUID", required = true)
            @PathVariable UUID sectionId
    ) throws SectionNotFoundException {

        List<Thread> threads = getUseCase.getAllBySectionId(sectionId);
        List<ThreadQuery> query = threads.stream().map(mapper::toQuery).toList();

        return ResponseEntity.ok(query);
    }

    /**
     * Creates a new thread.
     *
     * @param request request containing thread information
     * @return created thread information
     * @throws SectionNotFoundException if the associated section cannot be found
     * @throws ProfileNotFoundException if the associated profile cannot be found
     */
    @PostMapping
    @RolesAllowed({"ADMIN", "USER"})
    @Operation(
            operationId = "createThread",
            summary = "Create a thread",
            description = "Creates a new thread inside a section",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Thread created successfully",
                            content = @Content(schema = @Schema(implementation = ThreadQuery.class))
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
                            responseCode = "404",
                            description = "Section or Profile not found",
                            content = @Content(schema = @Schema(implementation = ErrorMessageQuery.class))
                    )
            }
    )
    public ResponseEntity<ThreadQuery> createThread(
            @Valid @RequestBody ThreadCreateRequest request
    ) throws SectionNotFoundException, ProfileNotFoundException {

        Thread thread = postUseCase.create(request);
        ThreadQuery query = mapper.toQuery(thread);

        return ResponseEntity.status(CREATED).body(query);
    }

    /**
     * Updates an existing thread.
     *
     * @param threadId unique identifier of the thread to update
     * @param request request containing updated thread information
     * @return updated thread information
     * @throws ThreadNotFoundException if no thread is found with the specified identifier
     * @throws SectionNotFoundException if the associated section cannot be found
     */
    @PutMapping("/{threadId}")
    @RolesAllowed({"ADMIN", "USER"})
    @Operation(
            operationId = "updateThread",
            summary = "Update a thread",
            description = "Updates an existing thread (requires ownership check)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Thread updated successfully",
                            content = @Content(schema = @Schema(implementation = ThreadQuery.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "User is not allowed to update this thread",
                            content = @Content(schema = @Schema(implementation = ErrorMessageQuery.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Thread or Section not found",
                            content = @Content(schema = @Schema(implementation = ErrorMessageQuery.class))
                    )
            }
    )
    public ResponseEntity<ThreadQuery> updateThread(
            @Parameter(description = "Thread UUID", required = true) @PathVariable UUID threadId,
            @RequestBody ThreadUpdateRequest request,
            Authentication authentication
    ) throws ThreadNotFoundException, SectionNotFoundException, ProfileNotAllowedException, ProfileNotFoundException {

        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID userId = UUID.fromString(Objects.requireNonNull(jwt).getSubject());
        Profile user = profileGetUseCase.getById(userId);

        Thread thread = updateUseCase.update(user, threadId, request);
        ThreadQuery query = mapper.toQuery(thread);

        return ResponseEntity.ok(query);
    }

    /**
     * Deletes a thread.
     *
     * @param threadId unique identifier of the thread to delete
     * @return confirmation message
     * @throws ThreadNotFoundException if no thread is found with the specified identifier
     */
    @DeleteMapping("/{threadId}")
    @RolesAllowed({"ADMIN", "USER"})
    @Operation(
            operationId = "deleteThread",
            summary = "Delete a thread",
            description = "Deletes a thread (soft or hard depending on implementation)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Thread deleted successfully",
                            content = @Content(schema = @Schema(implementation = MessageResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "User is not allowed to delete this thread",
                            content = @Content(schema = @Schema(implementation = ErrorMessageQuery.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Thread not found",
                            content = @Content(schema = @Schema(implementation = ErrorMessageQuery.class))
                    )
            }
    )
    public ResponseEntity<MessageResponse> deleteThread(
            @Parameter(description = "Thread UUID", required = true) @PathVariable UUID threadId,
            Authentication authentication
    ) throws ThreadNotFoundException, ProfileNotAllowedException, ProfileNotFoundException {

        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID userId = UUID.fromString(Objects.requireNonNull(jwt).getSubject());
        Profile user = profileGetUseCase.getById(userId);

        deleteUseCase.delete(user, threadId);

        return ResponseEntity.ok(new MessageResponse("Thread with id: " + threadId + " is correctly deleted"));
    }
}
