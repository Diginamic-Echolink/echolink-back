package fr.diginamic.echolink.application.thread.port.in;

import fr.diginamic.echolink.domain.thread.exception.ThreadNotFoundException;

import java.util.UUID;

/**
 * Defines the use case for deleting a thread.
 */
public interface ThreadDeleteUseCase {

    /**
     * Deletes the thread identified by the specified identifier.
     *
     * @param id unique identifier of the thread to delete
     * @throws ThreadNotFoundException if no thread is found with the specified identifier
     */
    void delete(UUID id) throws ThreadNotFoundException;
}
