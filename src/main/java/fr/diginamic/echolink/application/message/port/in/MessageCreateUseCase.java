package fr.diginamic.echolink.application.message.port.in;

import fr.diginamic.echolink.domain.message.Message;
import fr.diginamic.echolink.domain.message.MessageCreateRequest;
import fr.diginamic.echolink.domain.message.MessageUpdateRequest;
import fr.diginamic.echolink.domain.message.exception.MessageNotFoundsException;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;
import fr.diginamic.echolink.domain.thread.exception.ThreadNotFoundException;
import jakarta.validation.Valid;

public interface MessageCreateUseCase {

    Message create(MessageCreateRequest request) throws ThreadNotFoundException, ProfileNotFoundException;
}
