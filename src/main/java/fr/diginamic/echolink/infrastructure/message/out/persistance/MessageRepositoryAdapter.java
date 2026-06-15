package fr.diginamic.echolink.infrastructure.message.out.persistance;

import fr.diginamic.echolink.application.message.port.out.MessageRepository;
import fr.diginamic.echolink.domain.message.Message;
import fr.diginamic.echolink.infrastructure.message.out.persistance.repository.MessageJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Adapter implementation of {@link MessageRepository} using Spring Data JPA.
 * <p>
 * This class acts as a bridge between the domain layer and the persistence layer,
 * delegating operations to {@link MessageJdbcRepository}.
 */
@Component
@RequiredArgsConstructor
public class MessageRepositoryAdapter implements MessageRepository {

    /**
     * Underlying JPA repository used by this adapter to perform persistence operations.
     */
    private final MessageJdbcRepository repository;

    @Override
    public Optional<Message> getById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Message> getAllByProfileId(UUID profileId) {
        return repository.findAllByProfileId(profileId);
    }

    @Override
    public List<Message> getAllByThreadId(UUID threadId) {
        return repository.findAllByThreadId(threadId);
    }

    @Override
    public Page<Message> getAllByThreadId(UUID threadId, Pageable pageable) {
        return repository.findAllByThreadId(threadId, pageable);
    }

    @Override
    public Message save(Message message) {
        return repository.save(message);
    }

    @Override
    public void removeProfileReferences(UUID profileId) {
        getAllByProfileId(profileId).forEach(message -> {
            message.setProfile(null);
            repository.save(message);
        });
    }
}
