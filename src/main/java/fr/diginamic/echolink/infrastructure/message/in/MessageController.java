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
import fr.diginamic.echolink.infrastructure.common.in.dto.MessageResponse;
import fr.diginamic.echolink.infrastructure.message.in.dto.MessageQuery;
import fr.diginamic.echolink.infrastructure.message.in.mapper.MessageQueryMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<MessageQuery>> getMessageByThreadId(
            @PathVariable UUID threadId
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
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<MessageQuery> createMessage(
            @Valid @RequestBody MessageCreateRequest request
    ) throws ThreadNotFoundException, ProfileNotFoundException {
        Message message = createUseCase.create(request);
        MessageQuery query = mapper.toQuery(message);
        return ResponseEntity.ok(query);
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
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<MessageQuery> updateMessage(
            @PathVariable UUID messageId,
            @RequestBody MessageUpdateRequest request,
            Authentication authentication
    ) throws ProfileNotFoundException, MessageNotFoundException, ProfileNotAllowedException {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID profileId = UUID.fromString(Objects.requireNonNull(jwt).getSubject());
        Profile profile = profileGetUseCase.getById(profileId);

        Message message = updateUseCase.update(profile, messageId, request);
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
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<MessageResponse> deleteMessage(
            @PathVariable UUID messageId,
            Authentication authentication
    ) throws MessageNotFoundException, ProfileNotFoundException, ProfileNotAllowedException {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID profileId = UUID.fromString(Objects.requireNonNull(jwt).getSubject());
        Profile profile = profileGetUseCase.getById(profileId);

        deleteUseCase.delete(profile, messageId);
        return ResponseEntity.ok(new MessageResponse("Message with id: " + messageId + " is correctly deleted"));
    }
}
