package fr.diginamic.echolink.application.message.port.in;

import fr.diginamic.echolink.domain.message.Message;
import fr.diginamic.echolink.domain.message.exception.MessageNotFoundException;
import fr.diginamic.echolink.domain.thread.exception.ThreadNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Use case responsible for retrieving message data.
 * <p>
 * Provides read operations for messages, either by their unique identifier
 * or by their associated thread.
 */
public interface MessageGetUseCase {

    /**
     * Retrieves a message by its unique identifier.
     *
     * @param id unique identifier of the message
     * @return the corresponding message
     * @throws MessageNotFoundException if no message is found with the given id
     */
    Message getById(UUID id) throws MessageNotFoundException;

    /**
     * Retrieves all messages belonging to a specific thread.
     *
     * @param id unique identifier of the thread
     * @return list of messages associated with the thread
     * @throws ThreadNotFoundException if the thread does not exist
     */
    List<Message> getAllByThreadId(UUID id) throws ThreadNotFoundException;

    /**
     * Retrieves paginated messages belonging to a thread.
     *
     * @param threadId unique identifier of the thread
     * @param pageable pagination and sorting information
     * @return paginated list of messages
     * @throws ThreadNotFoundException if thread does not exist
     */
    Page<Message> getAllByThreadId(UUID threadId, Pageable pageable) throws ThreadNotFoundException;
}
