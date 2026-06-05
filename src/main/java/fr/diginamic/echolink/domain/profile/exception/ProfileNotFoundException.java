package fr.diginamic.echolink.domain.profile.exception;

import fr.diginamic.echolink.domain.shared.exception.AbstractNotFoundException;

/**
 * Exception thrown when a requested profile cannot be found.
 */
public class ProfileNotFoundException extends AbstractNotFoundException {

    /**
     * Creates a new profile not found exception with the specified message.
     *
     * @param message detail message describing the error
     */
    public ProfileNotFoundException(String message) {
        super(message);
    }
}
