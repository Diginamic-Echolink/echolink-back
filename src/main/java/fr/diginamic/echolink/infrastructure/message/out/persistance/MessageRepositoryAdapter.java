package fr.diginamic.echolink.infrastructure.message.out.persistance;

import fr.diginamic.echolink.application.message.port.out.MessageRepository;
import fr.diginamic.echolink.domain.message.Message;
import fr.diginamic.echolink.infrastructure.message.out.persistance.repository.MessageJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MessageRepositoryAdapter implements MessageRepository {

    private final MessageJdbcRepository repository;

    @Override
    public Optional<Message> getById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Message> getAllByThread(UUID id) {
        return repository.findByThreadId(id);
    }

    @Override
    public Message save(Message message) {
        return repository.save(message);
    }
}
