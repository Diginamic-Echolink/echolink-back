package fr.diginamic.echolink.domain.airquality.exception;

import fr.diginamic.echolink.domain.shared.exception.AbstractNotFoundException;

/**
 * Exception thrown when no air quality data exists for a given location.
 */
public class AirQualityNotFoundException extends AbstractNotFoundException {

    /**
     * Creates a new {@code AirQualityNotFoundException} with the specified detail message.
     *
     * @param message the detail message describing the reason for the exception
     */
    public AirQualityNotFoundException(String message) {
        super(message);
    }
}
