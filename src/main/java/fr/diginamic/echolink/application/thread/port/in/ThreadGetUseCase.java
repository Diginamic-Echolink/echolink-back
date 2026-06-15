package fr.diginamic.echolink.application.thread.port.in;

import fr.diginamic.echolink.domain.section.exception.SectionNotFoundException;
import fr.diginamic.echolink.domain.thread.Thread;
import fr.diginamic.echolink.domain.thread.exception.ThreadNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * Retrieves all threads belonging to a given section.
     *
     * @param sectionId unique identifier of the section
     * @return paginated list of threads
     * @throws SectionNotFoundException if the section does not exist
     */
    List<Thread> getAllBySectionId(UUID sectionId) throws SectionNotFoundException;

    /**
     * Retrieves paginated threads belonging to a given section.
     *
     * @param sectionId unique identifier of the section
     * @param pageable pagination and sorting information
     * @return paginated list of threads
     * @throws SectionNotFoundException if the section does not exist
     */
    Page<Thread> getAllBySectionId(UUID sectionId, Pageable pageable) throws SectionNotFoundException;
}
