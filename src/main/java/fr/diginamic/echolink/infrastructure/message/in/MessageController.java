package fr.diginamic.echolink.infrastructure.message.in;

import fr.diginamic.echolink.application.message.port.in.MessageCreateUseCase;
import fr.diginamic.echolink.application.message.port.in.MessageDeleteUseCase;
import fr.diginamic.echolink.application.message.port.in.MessageGetUseCase;
import fr.diginamic.echolink.application.message.port.in.MessageUpdateUseCase;
import fr.diginamic.echolink.application.profile.port.in.ProfileGetUseCase;
import fr.diginamic.echolink.domain.message.Message;
import fr.diginamic.echolink.domain.message.MessageCreateRequest;
import fr.diginamic.echolink.domain.message.MessageUpdateRequest;
import fr.diginamic.echolink.domain.message.exception.MessageNotFoundException;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotAllowedException;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;
import fr.diginamic.echolink.domain.thread.exception.ThreadNotFoundException;
import fr.diginamic.echolink.infrastructure.common.in.dto.ErrorMessageQuery;
import fr.diginamic.echolink.infrastructure.common.in.dto.MessageResponse;
import fr.diginamic.echolink.infrastructure.message.in.dto.MessageQuery;
import fr.diginamic.echolink.infrastructure.message.in.mapper.MessageQueryMapper;
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
 * REST controller responsible for handling message-related operations.
 * <p>
 * Provides endpoints to create, retrieve, update and delete messages
 * within a thread-based discussion system.
 * <p>
 * All operations require authentication and are restricted to users
 * with {@code ROLE_USER} or {@code ROLE_ADMIN}.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/message", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Message", description = "Message management")
public class MessageController {

    /**
     * Use case responsible for retrieving messages.
     */
    private final MessageGetUseCase getUseCase;

    /**
     * Use case responsible for creating messages.
     */
    private final MessageCreateUseCase createUseCase;

    /**
     * Use case responsible for updating messages.
     */
    private final MessageUpdateUseCase updateUseCase;

    /**
     * Use case responsible for deleting messages.
     */
    private final MessageDeleteUseCase deleteUseCase;

    /**
     * Use case used to retrieve profiles linked to authentication.
     */
    private final ProfileGetUseCase profileGetUseCase;

    /**
     * Mapper used to convert Message domain objects into query DTOs.
     */
    private final MessageQueryMapper mapper;

    /**
     * Retrieves all messages belonging to a thread.
     *
     * @param threadId unique identifier of the thread
     * @return list of messages for the specified thread
     * @throws ThreadNotFoundException if the thread does not exist
     */
    @GetMapping("/all/{threadId}")
    @RolesAllowed({"ADMIN", "USER"})
    @Operation(
            operationId = "getMessagesByThreadId",
            summary = "Get all messages from a thread",
            description = "Returns all messages belonging to the specified thread",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Messages retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = MessageQuery.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Thread not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageQuery.class)
                            )
                    )
            }
    )
    public ResponseEntity<List<MessageQuery>> getMessageByThreadId(
            @Parameter(description = "Thread UUID", required = true) @PathVariable UUID threadId
    ) throws ThreadNotFoundException {

        List<Message> messages = getUseCase.getAllByThreadId(threadId);
        List<MessageQuery> query = messages.stream().map(mapper::toQuery).toList();

        return ResponseEntity.ok(query);
    }

    /**
     * Creates a new message in a thread.
     *
     * @param request message creation payload
     * @return created message
     * @throws ThreadNotFoundException if the thread does not exist
     * @throws ProfileNotFoundException if the author profile does not exist
     */
    @PostMapping
    @RolesAllowed({"ADMIN", "USER"})
    @Operation(
            operationId = "createMessage",
            summary = "Create a message",
            description = "Creates a new message inside a thread",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Message created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageQuery.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageQuery.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Thread or Profile not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageQuery.class)
                            )
                    )
            }
    )
    public ResponseEntity<MessageQuery> createMessage(
            @Valid @RequestBody MessageCreateRequest request
    ) throws ThreadNotFoundException, ProfileNotFoundException {

        Message message = createUseCase.create(request);
        MessageQuery query = mapper.toQuery(message);

        return ResponseEntity.status(CREATED).body(query);
    }

    /**
     * Updates an existing message.
     *
     * @param messageId unique identifier of the message
     * @param request update payload containing modified fields
     * @param authentication current authenticated user context
     * @return updated message
     * @throws ProfileNotFoundException if the profile does not exist
     * @throws MessageNotFoundException if the message does not exist
     * @throws ProfileNotAllowedException if the user is not allowed to update the message
     */
    @PutMapping("/{messageId}")
    @RolesAllowed({"ADMIN", "USER"})
    @Operation(
            operationId = "updateMessage",
            summary = "Update a message",
            description = "Updates an existing message owned by the authenticated user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Message updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageQuery.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "User is not allowed to update this message",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageQuery.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Message or Profile not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageQuery.class)
                            )
                    )
            }
    )
    public ResponseEntity<MessageQuery> updateMessage(
            @Parameter(description = "Message UUID", required = true) @PathVariable UUID messageId,
            @RequestBody MessageUpdateRequest request,
            Authentication authentication
    ) throws ProfileNotFoundException, MessageNotFoundException, ProfileNotAllowedException {

        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID userId = UUID.fromString(Objects.requireNonNull(jwt).getSubject());
        Profile user = profileGetUseCase.getById(userId);

        Message message = updateUseCase.update(user, messageId, request);
        MessageQuery query = mapper.toQuery(message);

        return ResponseEntity.ok(query);
    }

    /**
     * Deletes a message (soft delete by content replacement).
     *
     * @param messageId unique identifier of the message
     * @param authentication current authenticated user context
     * @return confirmation response message
     * @throws MessageNotFoundException if the message does not exist
     * @throws ProfileNotFoundException if the profile does not exist
     * @throws ProfileNotAllowedException if the user is not allowed to delete the message
     */
    @DeleteMapping("/{messageId}")
    @RolesAllowed({"ADMIN", "USER"})
    @Operation(
            operationId = "deleteMessage",
            summary = "Delete a message",
            description = "Soft deletes a message by replacing its content",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Message deleted successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "User is not allowed to delete this message",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageQuery.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Message or Profile not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageQuery.class)
                            )
                    )
            }
    )
    public ResponseEntity<MessageResponse> deleteMessage(
            @Parameter(description = "Message UUID", required = true) @PathVariable UUID messageId,
            Authentication authentication
    ) throws MessageNotFoundException, ProfileNotFoundException, ProfileNotAllowedException {

        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID userId = UUID.fromString(Objects.requireNonNull(jwt).getSubject());
        Profile user = profileGetUseCase.getById(userId);

        deleteUseCase.delete(user, messageId);

        return ResponseEntity.ok(new MessageResponse("Message with id: " + messageId + " is correctly deleted"));
    }
}
