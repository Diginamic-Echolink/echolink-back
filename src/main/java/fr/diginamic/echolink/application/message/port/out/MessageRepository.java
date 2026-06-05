package fr.diginamic.echolink.application.message.port.out;

import fr.diginamic.echolink.domain.message.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository {

    Optional<Message> getById(UUID id);

    List<Message> getAllByThread(UUID id);

    Message save(Message message);
}
