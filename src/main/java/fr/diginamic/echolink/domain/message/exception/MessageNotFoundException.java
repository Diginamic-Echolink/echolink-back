package fr.diginamic.echolink.domain.message.exception;

import fr.diginamic.echolink.domain.shared.exception.AbstractNotFoundException;

/**
 * Exception thrown when a {@code Message} entity cannot be found.
 * <p>
 * This exception is typically raised when attempting to retrieve,
 * update, or delete a message that does not exist in the system.
 */
public class MessageNotFoundException extends AbstractNotFoundException {

    /**
     * Constructs a new MessageNotFoundException with the specified detail message.
     *
     * @param message detailed error message explaining the cause of the exception
     */
    public MessageNotFoundException(String message) {
        super(message);
    }
}
