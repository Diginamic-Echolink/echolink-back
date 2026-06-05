package fr.diginamic.echolink.application.message.port.in;

import fr.diginamic.echolink.domain.message.exception.MessageNotFoundsException;

import java.util.UUID;

public interface MessageDeleteUseCase {

    void delete(UUID id) throws MessageNotFoundsException;
}
