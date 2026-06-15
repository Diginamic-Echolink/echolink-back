package fr.diginamic.echolink.application.message.port.out;

import fr.diginamic.echolink.domain.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * Retrieves all messages associated with a profile.
     *
     * @param profileId unique identifier of the profile
     * @return list of messages belonging to the profile
     */
    List<Message> getAllByProfileId(UUID profileId);

    /**
     * Retrieves all messages associated with a specific thread.
     *
     * @param threadId unique identifier of the thread
     * @return list of messages belonging to the given thread
     */
    List<Message> getAllByThreadId(UUID threadId);

    /**
     * Retrieves paginated messages belonging to a given thread.
     *
     * @param threadId unique identifier of the thread
     * @param pageable pagination and sorting information
     * @return paginated list of messages belonging to the given thread
     */
    Page<Message> getAllByThreadId(UUID threadId, Pageable pageable);

    /**
     * Persists a message entity.
     *
     * @param message message to save or update
     * @return the persisted message
     */
    Message save(Message message);

    /**
     * Removes the profile association from all messages authored by the specified profile.
     *
     * @param profileId unique identifier of the profile
     */
    void removeProfileReferences(UUID profileId);
}
