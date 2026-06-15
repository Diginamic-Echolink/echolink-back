package fr.diginamic.echolink.application.thread.port.out;

import fr.diginamic.echolink.domain.thread.Thread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * Retrieves all threads associated with a profile.
     *
     * @param profileId unique identifier of the profile
     * @return list of threads belonging to the profile
     */
    List<Thread> getAllByProfileId(UUID profileId);

    /**
     * Retrieves all threads associated with a section.
     *
     * @param sectionId unique identifier of the section
     * @return list of threads belonging to the section
     */
    List<Thread> getAllBySectionId(UUID sectionId);

    /**
     * Retrieves paginated threads belonging to a given section.
     *
     * @param sectionId unique identifier of the section
     * @param pageable pagination and sorting information
     * @return paginated list of threads
     */
    Page<Thread> getAllBySectionId(UUID sectionId, Pageable pageable);

    /**
     * Persists a thread.
     *
     * @param thread thread to save
     * @return the saved thread
     */
    Thread save(Thread thread);

    /**
     * Removes the profile association from all threads created by the specified profile.
     *
     * @param profileId unique identifier of the profile
     */
    void removeProfileReferences(UUID profileId);
}
