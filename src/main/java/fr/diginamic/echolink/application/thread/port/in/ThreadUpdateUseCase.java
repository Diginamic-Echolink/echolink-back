package fr.diginamic.echolink.application.thread.port.in;

import fr.diginamic.echolink.domain.section.exception.SectionNotFoundException;
import fr.diginamic.echolink.domain.thread.Thread;
import fr.diginamic.echolink.domain.thread.ThreadUpdateRequest;
import fr.diginamic.echolink.domain.thread.exception.ThreadNotFoundException;

import java.util.UUID;

public interface ThreadUpdateUseCase {

    Thread update(UUID id, ThreadUpdateRequest request) throws ThreadNotFoundException, SectionNotFoundException;
}
