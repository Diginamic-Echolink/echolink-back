package fr.diginamic.echolink.application.message.port.in;

import fr.diginamic.echolink.domain.message.Message;
import fr.diginamic.echolink.domain.message.exception.MessageNotFoundsException;

import java.util.List;
import java.util.UUID;

public interface MessageGetUseCase {

    Message getById(UUID id) throws MessageNotFoundsException;

    List<Message> getAllByThread(UUID id);
}
