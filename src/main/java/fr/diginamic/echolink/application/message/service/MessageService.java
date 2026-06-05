package fr.diginamic.echolink.application.message.service;

import fr.diginamic.echolink.application.message.port.in.MessageCreateUseCase;
import fr.diginamic.echolink.application.message.port.in.MessageDeleteUseCase;
import fr.diginamic.echolink.application.message.port.in.MessageGetUseCase;
import fr.diginamic.echolink.application.message.port.in.MessageUpdateUseCase;
import fr.diginamic.echolink.application.message.port.out.MessageRepository;
import fr.diginamic.echolink.application.profile.port.in.ProfileGetUseCase;
import fr.diginamic.echolink.application.thread.port.in.ThreadGetUseCase;
import fr.diginamic.echolink.domain.message.Message;
import fr.diginamic.echolink.domain.message.MessageCreateRequest;
import fr.diginamic.echolink.domain.message.MessageUpdateRequest;
import fr.diginamic.echolink.domain.message.exception.MessageNotFoundException;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotAllowedException;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;
import fr.diginamic.echolink.domain.thread.Thread;
import fr.diginamic.echolink.domain.thread.exception.ThreadNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service responsible for managing messages.
 * <p>
 * Provides business operations such as retrieval, creation, update, and deletion
 * of messages. Enforces authorization rules ensuring that only message owners
 * or administrators can modify or delete messages.
 */
@Service
@RequiredArgsConstructor
public class MessageService
        implements MessageGetUseCase, MessageCreateUseCase, MessageUpdateUseCase, MessageDeleteUseCase {

    /**
     * Use case responsible for retrieving threads.
     */
    private final ThreadGetUseCase threadGetUseCase;

    /**
     * Use case responsible for retrieving profiles.
     */
    private final ProfileGetUseCase profileGetUseCase;

    /**
     * Repository used to persist and retrieve messages.
     */
    private final MessageRepository repository;

    @Override
    public Message getById(UUID id) throws MessageNotFoundException {
        return repository.getById(id)
                .orElseThrow(() -> new MessageNotFoundException("Message with id " + id + " not found"));
    }

    @Override
    public List<Message> getAllByThreadId(UUID id) throws ThreadNotFoundException {
        Thread thread = threadGetUseCase.getById(id);
        return repository.getAllByThreadId(thread.getId());
    }

    @Override
    public Message create(MessageCreateRequest request) throws ThreadNotFoundException, ProfileNotFoundException {
        Thread thread = threadGetUseCase.getById(request.threadId());
        Profile profile = profileGetUseCase.getById(request.profileId());

        Message message = new Message(
                request.text(),
                profile,
                thread
        );
        return repository.save(message);
    }

    @Override
    public Message update(
            Profile profile,
            UUID id,
            MessageUpdateRequest request
    ) throws MessageNotFoundException, ProfileNotAllowedException {

        Message message = getById(id);

        if (!profile.isAdmin() && !profile.getId().equals(message.getProfile().getId())) {
            throw new ProfileNotAllowedException("You are not allowed to modify this message");
        }

        if (request.text() != null && !request.text().isBlank()) {
            message.setText(request.text());
        }

        return repository.save(message);
    }

    @Override
    public void delete(
            Profile profile,
            UUID id
    ) throws MessageNotFoundException, ProfileNotAllowedException {

        Message message = getById(id);

        if (!profile.isAdmin() && !profile.getId().equals(message.getProfile().getId())) {
            throw new ProfileNotAllowedException("You are not allowed to delete this message");
        }

        message.setText("This message was deleted by the moderator or the user");

        repository.save(message);
    }
}
