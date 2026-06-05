package fr.diginamic.echolink.application.message.port.in;

import fr.diginamic.echolink.domain.message.Message;
import fr.diginamic.echolink.domain.message.MessageCreateRequest;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;
import fr.diginamic.echolink.domain.thread.exception.ThreadNotFoundException;

/**
 * Use case responsible for creating new {@link Message} instances.
 * <p>
 * This interface defines the application-level contract for message creation,
 * including validation of referenced entities such as {@link Thread} and {@link Profile}.
 */
public interface MessageCreateUseCase {

    /**
     * Creates a new message based on the provided request.
     *
     * @param request data required to create the message
     * @return the created message
     * @throws ThreadNotFoundException if the referenced thread does not exist
     * @throws ProfileNotFoundException if the referenced profile does not exist
     */
    Message create(MessageCreateRequest request) throws ThreadNotFoundException, ProfileNotFoundException;
}
