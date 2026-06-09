package fr.diginamic.echolink.domain.meteo.exception;

import fr.diginamic.echolink.domain.shared.exception.AbstractNotFoundException;

/**
 * Exception thrown when a weather record cannot be found.
 */
public class MeteoNotFoundException extends AbstractNotFoundException {

    /**
     * Creates a new {@code MeteoNotFoundException} with the specified detail message.
     *
     * @param message the detail message describing the reason for the exception
     */
    public MeteoNotFoundException(String message) {
        super(message);
    }
}
