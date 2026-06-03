package fr.diginamic.echolink.application.thread.port.in;

import fr.diginamic.echolink.domain.thread.exception.ThreadNotFoundException;

import java.util.UUID;

public interface ThreadDeleteUseCase {

    void delete(UUID id) throws ThreadNotFoundException;
}
