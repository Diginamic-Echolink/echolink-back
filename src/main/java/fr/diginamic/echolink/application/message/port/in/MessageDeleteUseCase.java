package fr.diginamic.echolink.application.message.port.in;

import fr.diginamic.echolink.domain.message.exception.MessageNotFoundException;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotAllowedException;

import java.util.UUID;

/**
 * Use case responsible for deleting messages.
 * <p>
 * This operation applies business rules to ensure that only authorized profiles
 * can delete a message.
 */
public interface MessageDeleteUseCase {

    /**
     * Deletes a message identified by its unique identifier.
     * <p>
     * Deletion is subject to authorization rules:
     * only the message owner or an administrator is allowed to perform this action.
     *
     * @param profile the profile requesting the deletion
     * @param id unique identifier of the message to delete
     * @throws MessageNotFoundException if the message does not exist
     * @throws ProfileNotAllowedException if the profile is not authorized to delete the message
     */
    void delete(Profile profile, UUID id) throws MessageNotFoundException, ProfileNotAllowedException;
}
