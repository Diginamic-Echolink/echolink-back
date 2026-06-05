package fr.diginamic.echolink.domain.thread.exception;

import fr.diginamic.echolink.domain.shared.exception.AbstractNotFoundException;

/**
 * Exception thrown when a requested thread cannot be found.
 */
public class ThreadNotFoundException extends AbstractNotFoundException {

    /**
     * Creates a new thread not found exception with the specified message.
     *
     * @param message detail message describing the error
     */
    public ThreadNotFoundException(String message) {
        super(message);
    }
}
