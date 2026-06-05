package fr.diginamic.echolink.application.message.port.in;

import fr.diginamic.echolink.domain.message.Message;
import fr.diginamic.echolink.domain.message.MessageUpdateRequest;
import fr.diginamic.echolink.domain.message.exception.MessageNotFoundException;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotAllowedException;

import java.util.UUID;

/**
 * Use case responsible for updating an existing message.
 * <p>
 * This operation applies business rules to ensure that only authorized profiles
 * (message owner or administrator) can modify a message.
 */
public interface MessageUpdateUseCase {

    /**
     * Updates an existing message.
     *
     * @param profile the profile requesting the update
     * @param id unique identifier of the message to update
     * @param message update payload containing modified fields
     * @return the updated message
     * @throws MessageNotFoundException if no message is found with the given id
     * @throws ProfileNotAllowedException if the profile is not authorized to update the message
     */
    Message update(Profile profile, UUID id, MessageUpdateRequest message)
            throws MessageNotFoundException, ProfileNotAllowedException;
}
