package fr.diginamic.echolink.application.thread.port.in;

import fr.diginamic.echolink.domain.thread.Thread;
import fr.diginamic.echolink.domain.thread.exception.ThreadNotFoundException;

import java.util.List;
import java.util.UUID;

public interface ThreadGetUseCase {

    Thread getById(UUID id) throws ThreadNotFoundException;

    List<Thread> getAllBySectionId(UUID sectionId);
}
