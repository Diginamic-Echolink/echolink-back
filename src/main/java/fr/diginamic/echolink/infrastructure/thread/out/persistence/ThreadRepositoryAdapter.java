package fr.diginamic.echolink.infrastructure.thread.out.persistence;

import fr.diginamic.echolink.application.thread.port.out.ThreadRepository;
import fr.diginamic.echolink.domain.thread.Thread;
import fr.diginamic.echolink.infrastructure.thread.out.persistence.repository.ThreadJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ThreadRepositoryAdapter implements ThreadRepository {

    private final ThreadJdbcRepository repository;

    @Override
    public Optional<Thread> getById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Thread> getAllBySectionId(UUID sectionId) {
        return repository.findAllBySectionId(sectionId);
    }

    @Override
    public Thread save(Thread thread) {
        return repository.save(thread);
    }
}
