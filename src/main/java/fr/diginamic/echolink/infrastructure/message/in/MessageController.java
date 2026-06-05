package fr.diginamic.echolink.infrastructure.message.in;

import fr.diginamic.echolink.application.message.port.in.MessageCreateUseCase;
import fr.diginamic.echolink.application.message.port.in.MessageDeleteUseCase;
import fr.diginamic.echolink.application.message.port.in.MessageGetUseCase;
import fr.diginamic.echolink.application.message.port.in.MessageUpdateUseCase;
import fr.diginamic.echolink.domain.message.Message;
import fr.diginamic.echolink.domain.message.MessageCreateRequest;
import fr.diginamic.echolink.domain.message.MessageUpdateRequest;
import fr.diginamic.echolink.domain.message.exception.MessageNotFoundsException;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;
import fr.diginamic.echolink.domain.thread.exception.ThreadNotFoundException;
import fr.diginamic.echolink.infrastructure.message.in.dto.MessageQuery;
import fr.diginamic.echolink.infrastructure.message.in.mapper.MessageQueryMapper;
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
@RequestMapping(value = "/api/v1/message", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {

    private final MessageGetUseCase getUseCase;
    private final MessageCreateUseCase createUseCase;
    private final MessageUpdateUseCase updateUseCase;
    private final MessageDeleteUseCase deleteUseCase;

    private final MessageQueryMapper mapper;

    //@GetMapping("/all/{messageId}/{threadId}")
    @GetMapping("/all/{threadId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<MessageQuery>> getMessageByThread(
            @PathVariable UUID threadId
    ) {
        List<Message> messages = getUseCase.getAllByThread(threadId);
        List<MessageQuery> query = messages.stream().map(mapper::toQuery).toList();
        return ResponseEntity.ok(query);
    }

    @PostMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<MessageQuery> createMessage(
            @Valid @RequestBody MessageCreateRequest request
    ) throws ThreadNotFoundException, ProfileNotFoundException {
        Message message = createUseCase.create(request);
        MessageQuery query = mapper.toQuery(message);
        return ResponseEntity.ok(query);
    }

    @PutMapping("/{messageId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<MessageQuery> updateMessage(
            @PathVariable UUID messageId,
            @Valid @RequestBody MessageUpdateRequest request
    ) throws ThreadNotFoundException, ProfileNotFoundException, MessageNotFoundsException {
        Message message = updateUseCase.update(messageId, request);
        MessageQuery query = mapper.toQuery(message);
        return ResponseEntity.ok(query);
    }

    @DeleteMapping("/{messageId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<?> deleteMessage(@PathVariable UUID messageId) throws MessageNotFoundsException, ProfileNotFoundException {
        deleteUseCase.delete(messageId);
        return ResponseEntity.ok().build();
    }
}
