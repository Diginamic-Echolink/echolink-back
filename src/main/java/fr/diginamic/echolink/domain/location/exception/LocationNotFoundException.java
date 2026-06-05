package fr.diginamic.echolink.domain.location.exception;

import fr.diginamic.echolink.domain.shared.exception.AbstractNotFoundException;

/**
 * Exception thrown when a requested location cannot be found.
 */
public class LocationNotFoundException extends AbstractNotFoundException {

    /**
     * Creates a new location not found exception with the specified message.
     *
     * @param message detail message describing the error
     */
    public LocationNotFoundException(String message) {
        super(message);
    }
}
