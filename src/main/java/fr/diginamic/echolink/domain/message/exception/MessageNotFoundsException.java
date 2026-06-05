package fr.diginamic.echolink.domain.message.exception;

import fr.diginamic.echolink.domain.shared.exception.AbstractNotFoundException;

public class MessageNotFoundsException extends AbstractNotFoundException {
    public MessageNotFoundsException(String message) {
        super(message);
    }
}
