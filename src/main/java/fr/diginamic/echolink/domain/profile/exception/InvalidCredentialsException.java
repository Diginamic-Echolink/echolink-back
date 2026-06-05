package fr.diginamic.echolink.domain.profile.exception;

import fr.diginamic.echolink.domain.shared.exception.AbstractBadRequestException;

/**
 * Exception thrown when authentication or registration credentials are invalid.
 */
public class InvalidCredentialsException extends AbstractBadRequestException {

    /**
     * Creates a new invalid credentials exception with the specified message.
     *
     * @param message detail message describing the validation error
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
