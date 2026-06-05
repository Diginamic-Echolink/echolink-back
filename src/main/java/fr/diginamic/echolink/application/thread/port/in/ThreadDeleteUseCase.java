package fr.diginamic.echolink.application.thread.port.in;

import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotAllowedException;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;
import fr.diginamic.echolink.domain.thread.exception.ThreadNotFoundException;

import java.util.UUID;

/**
 * Defines the use case for deleting a thread.
 */
public interface ThreadDeleteUseCase {

    /**
     * Deletes the thread identified by the specified identifier.
     *
     * @param profile identification of the user who claim this request
     * @param id unique identifier of the thread to delete
     * @throws ThreadNotFoundException if no thread is found with the specified identifier
     * @throws ProfileNotFoundException if current user is not correctly determined
     * @throws ProfileNotAllowedException if the user is not allowed to proceed to this modification
     */
    void delete(Profile profile, UUID id) throws ThreadNotFoundException, ProfileNotAllowedException, ProfileNotFoundException;
}
