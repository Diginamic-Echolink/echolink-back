package fr.diginamic.echolink.application.thread.port.in;

import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotAllowedException;
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
     * @param profile identification of the user who claim this request
     * @param id unique identifier of the thread to update
     * @param request request containing updated thread information
     * @return the updated thread
     * @throws ThreadNotFoundException if no thread is found with the specified identifier
     * @throws SectionNotFoundException if no section is found with the specified identifier
     * @throws ProfileNotAllowedException if the user is not allowed to proceed to this modification
     */
    Thread update(Profile profile, UUID id, ThreadUpdateRequest request)
            throws ThreadNotFoundException, SectionNotFoundException, ProfileNotAllowedException;
}
