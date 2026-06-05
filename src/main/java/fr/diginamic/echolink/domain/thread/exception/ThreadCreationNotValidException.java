package fr.diginamic.echolink.domain.thread.exception;

import fr.diginamic.echolink.domain.shared.exception.AbstractBadRequestException;

/**
 * Exception thrown when thread creation data is invalid.
 */
public class ThreadCreationNotValidException extends AbstractBadRequestException {

    /**
     * Creates a new thread creation validation exception with the specified message.
     *
     * @param message detail message describing the validation error
     */
    public ThreadCreationNotValidException(String message) {
        super(message);
    }
}
