package fr.diginamic.echolink.domain.thread.exception;

import fr.diginamic.echolink.domain.shared.exception.AbstractBadRequestException;

public class ThreadCreationNotValidException extends AbstractBadRequestException {

    public ThreadCreationNotValidException(String message) {
        super(message);
    }
}
