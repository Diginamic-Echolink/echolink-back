package fr.diginamic.echolink.application.thread.port.out;

import fr.diginamic.echolink.domain.thread.Thread;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Defines the contract for accessing and persisting thread data.
 */
public interface ThreadRepository {

    /**
     * Retrieves a thread by its unique identifier.
     *
     * @param id unique identifier of the thread
     * @return an {@link Optional} containing the thread if found
     */
    Optional<Thread> getById(UUID id);

    /**
     * Retrieves all threads associated with a section.
     *
     * @param sectionId unique identifier of the section
     * @return list of threads belonging to the section
     */
    List<Thread> getAllBySectionId(UUID sectionId);

    /**
     * Persists a thread.
     *
     * @param thread thread to save
     * @return the saved thread
     */
    Thread save(Thread thread);
}
