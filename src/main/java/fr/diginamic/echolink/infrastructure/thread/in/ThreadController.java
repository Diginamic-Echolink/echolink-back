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
import fr.diginamic.echolink.infrastructure.common.in.dto.MessageResponse;
import fr.diginamic.echolink.infrastructure.thread.in.dto.ThreadQuery;
import fr.diginamic.echolink.infrastructure.thread.in.mapper.ThreadQueryMapper;
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
 * REST controller exposing thread management endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/thread", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<ThreadQuery>> getAllBySectionId(
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
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<ThreadQuery> createThread(
            @Valid @RequestBody ThreadCreateRequest request
    ) throws SectionNotFoundException, ProfileNotFoundException {
        Thread thread = postUseCase.create(request);
        ThreadQuery query = mapper.toQuery(thread);
        return ResponseEntity.ok(query);
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
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<ThreadQuery> updateThread(
            @PathVariable UUID threadId,
            @RequestBody ThreadUpdateRequest request,
            Authentication authentication
    ) throws ThreadNotFoundException, SectionNotFoundException, ProfileNotAllowedException, ProfileNotFoundException {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID profileId = UUID.fromString(Objects.requireNonNull(jwt).getSubject());
        Profile profile = profileGetUseCase.getById(profileId);

        Thread thread = updateUseCase.update(profile, threadId, request);
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
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<MessageResponse> updateThread(
            @PathVariable UUID threadId,
            Authentication authentication
    ) throws ThreadNotFoundException, ProfileNotAllowedException, ProfileNotFoundException {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID profileId = UUID.fromString(Objects.requireNonNull(jwt).getSubject());
        Profile profile = profileGetUseCase.getById(profileId);

        deleteUseCase.delete(profile, threadId);
        return ResponseEntity.ok(new MessageResponse("Thread with id: " + threadId + " is correctly deleted"));
    }
}
