package fr.diginamic.echolink.application.thread.port.out;

import fr.diginamic.echolink.domain.thread.Thread;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ThreadRepository {

    Optional<Thread> getById(UUID id);

    List<Thread> getAllBySectionId(UUID sectionId);

    Thread save(Thread thread);
}
