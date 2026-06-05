package fr.diginamic.echolink.application.thread.port.in;

import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;
import fr.diginamic.echolink.domain.section.exception.SectionNotFoundException;
import fr.diginamic.echolink.domain.thread.Thread;
import fr.diginamic.echolink.domain.thread.ThreadUpdateRequest;
import fr.diginamic.echolink.domain.thread.exception.ThreadNotFoundException;

import java.util.UUID;

/**
 * Defines the use case for updating a thread.
 */
public interface ThreadUpdateUseCase {

    /**
     * Updates the thread identified by the specified identifier.
     *
     * @param id unique identifier of the thread to update
     * @param request request containing updated thread information
     * @return the updated thread
     * @throws ThreadNotFoundException if no thread is found with the specified identifier
     * @throws SectionNotFoundException if no section is found with the specified identifier
     * @throws ProfileNotFoundException if no profile is found with the specidied identifier
     */
    Thread update(UUID id, ThreadUpdateRequest request) throws ThreadNotFoundException, SectionNotFoundException, ProfileNotFoundException;
}
