package fr.diginamic.echolink.application.thread.port.in;

import fr.diginamic.echolink.domain.thread.Thread;
import fr.diginamic.echolink.domain.thread.exception.ThreadNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Defines the use cases for retrieving threads.
 */
public interface ThreadGetUseCase {

    /**
     * Retrieves a thread by its unique identifier.
     *
     * @param id unique identifier of the thread
     * @return the matching thread
     * @throws ThreadNotFoundException if no thread is found with the specified identifier
     */
    Thread getById(UUID id) throws ThreadNotFoundException;

    /**
     * Retrieves all threads associated with a section.
     *
     * @param sectionId unique identifier of the section
     * @return list of threads belonging to the section
     */
    List<Thread> getAllBySectionId(UUID sectionId);
}
