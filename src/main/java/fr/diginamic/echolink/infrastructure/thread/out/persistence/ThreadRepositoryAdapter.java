package fr.diginamic.echolink.infrastructure.thread.out.persistence;

import fr.diginamic.echolink.application.thread.port.out.ThreadRepository;
import fr.diginamic.echolink.domain.thread.Thread;
import fr.diginamic.echolink.infrastructure.thread.out.persistence.repository.ThreadJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Optional<Thread> getById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Thread> getAllByProfileId(UUID profileId) {
        return repository.findAllByProfileId(profileId);
    }

    @Override
    public List<Thread> getAllBySectionId(UUID sectionId) {
        return repository.findAllBySectionId(sectionId);
    }

    @Override
    public Page<Thread> getAllBySectionId(UUID sectionId, Pageable pageable) {
        return repository.findAllBySectionId(sectionId, pageable);
    }

    @Override
    public Thread save(Thread thread) {
        return repository.save(thread);
    }

    @Override
    public void removeProfileReferences(UUID profileId) {
        getAllByProfileId(profileId).forEach(thread -> {
            thread.setProfile(null);
            repository.save(thread);
        });
    }
}
