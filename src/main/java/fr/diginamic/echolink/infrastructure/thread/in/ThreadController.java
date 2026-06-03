package fr.diginamic.echolink.infrastructure.thread.in;

import fr.diginamic.echolink.application.thread.port.in.ThreadCreateUseCase;
import fr.diginamic.echolink.application.thread.port.in.ThreadDeleteUseCase;
import fr.diginamic.echolink.application.thread.port.in.ThreadGetUseCase;
import fr.diginamic.echolink.application.thread.port.in.ThreadUpdateUseCase;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;
import fr.diginamic.echolink.domain.section.exception.SectionNotFoundException;
import fr.diginamic.echolink.domain.thread.Thread;
import fr.diginamic.echolink.domain.thread.ThreadUpsertRequest;
import fr.diginamic.echolink.domain.thread.exception.ThreadCreationNotValidException;
import fr.diginamic.echolink.domain.thread.exception.ThreadNotFoundException;
import fr.diginamic.echolink.infrastructure.common.in.dto.MessageQuery;
import fr.diginamic.echolink.infrastructure.thread.in.dto.ThreadQuery;
import fr.diginamic.echolink.infrastructure.thread.in.mapper.ThreadQueryMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/thread", produces = MediaType.APPLICATION_JSON_VALUE)
public class ThreadController {

    private final ThreadGetUseCase getUseCase;
    private final ThreadCreateUseCase postUseCase;
    private final ThreadUpdateUseCase updateUseCase;
    private final ThreadDeleteUseCase deleteUseCase;

    private final ThreadQueryMapper mapper;

    @GetMapping("/all/{sectionId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<ThreadQuery>> getAllBySectionId(@PathVariable UUID sectionId) {
        List<Thread> threads = getUseCase.getAllBySectionId(sectionId);
        List<ThreadQuery> query = threads.stream().map(mapper::toQuery).toList();
        return ResponseEntity.ok(query);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ThreadQuery> createThread(
            @Valid @RequestBody ThreadUpsertRequest request
    ) throws ThreadCreationNotValidException, SectionNotFoundException, ProfileNotFoundException {
        Thread thread = postUseCase.create(request);
        ThreadQuery query = mapper.toQuery(thread);
        return ResponseEntity.ok(query);
    }

    @PutMapping("/{threadId}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<ThreadQuery> updateThread(
            @PathVariable UUID threadId,
            @Valid @RequestBody ThreadUpsertRequest request
    ) throws ThreadNotFoundException {
        Thread thread = updateUseCase.update(threadId, request);
        ThreadQuery query = mapper.toQuery(thread);
        return ResponseEntity.ok(query);
    }

    @DeleteMapping("/{threadId}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<MessageQuery> updateThread(@PathVariable UUID threadId) throws ThreadNotFoundException {
        deleteUseCase.delete(threadId);
        return ResponseEntity.ok(new MessageQuery("Thread with id: " + threadId + " is correctly deleted"));
    }
}
