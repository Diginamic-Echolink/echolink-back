package fr.diginamic.echolink.application.message.port.out;

import fr.diginamic.echolink.domain.message.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Port interface defining persistence operations for {@link Message}.
 * <p>
 * This abstraction allows the application layer to interact with message storage
 * without depending on any specific persistence technology (e.g., JPA, JDBC, etc.).
 */
public interface MessageRepository {

    /**
     * Retrieves a message by its unique identifier.
     *
     * @param id unique identifier of the message
     * @return an {@link Optional} containing the message if found, otherwise empty
     */
    Optional<Message> getById(UUID id);

    /**
     * Retrieves all messages associated with a specific thread.
     *
     * @param id unique identifier of the thread
     * @return list of messages belonging to the given thread
     */
    List<Message> getAllByThreadId(UUID id);

    /**
     * Persists a message entity.
     *
     * @param message message to save or update
     * @return the persisted message
     */
    Message save(Message message);
}
