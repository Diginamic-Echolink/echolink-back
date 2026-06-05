package fr.diginamic.echolink.infrastructure.thread.out.persistence;

import fr.diginamic.echolink.application.thread.port.out.ThreadRepository;
import fr.diginamic.echolink.domain.thread.Thread;
import fr.diginamic.echolink.infrastructure.thread.out.persistence.repository.ThreadJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Adapter implementing the thread repository port using a JPA repository.
 */
@Component
@RequiredArgsConstructor
public class ThreadRepositoryAdapter implements ThreadRepository {

    /**
     * Repository used to access thread persistence data.
     */
    private final ThreadJdbcRepository repository;

    /**
     * Retrieves a thread by its unique identifier.
     *
     * @param id unique identifier of the thread
     * @return an {@link Optional} containing the thread if found
     */
    @Override
    public Optional<Thread> getById(UUID id) {
        return repository.findById(id);
    }

    /**
     * Retrieves all threads associated with a section.
     *
     * @param sectionId unique identifier of the section
     * @return list of threads belonging to the section
     */
    @Override
    public List<Thread> getAllBySectionId(UUID sectionId) {
        return repository.findAllBySectionId(sectionId);
    }

    /**
     * Persists a thread.
     *
     * @param thread thread to save
     * @return the saved thread
     */
    @Override
    public Thread save(Thread thread) {
        return repository.save(thread);
    }
}
