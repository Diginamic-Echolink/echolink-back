package fr.diginamic.echolink.application.thread.port.in;

import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;
import fr.diginamic.echolink.domain.section.exception.SectionNotFoundException;
import fr.diginamic.echolink.domain.thread.Thread;
import fr.diginamic.echolink.domain.thread.ThreadCreateRequest;

/**
 * Defines the use case for creating a thread.
 */
public interface ThreadCreateUseCase {

    /**
     * Creates a new thread.
     *
     * @param request request containing thread information
     * @return the created thread
     * @throws SectionNotFoundException if the associated section cannot be found
     * @throws ProfileNotFoundException if the associated profile cannot be found
     */
    Thread create(ThreadCreateRequest request) throws SectionNotFoundException, ProfileNotFoundException;
}
