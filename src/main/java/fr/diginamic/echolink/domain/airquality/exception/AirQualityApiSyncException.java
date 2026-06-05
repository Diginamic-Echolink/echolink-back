package fr.diginamic.echolink.domain.airquality.exception;

/**
 * Exception thrown when an error occurs during air quality data synchronization
 * with an external API.
 */
public class AirQualityApiSyncException extends RuntimeException {

    /**
     * Creates a new exception with the specified detail message.
     *
     * @param message detail message describing the error
     */
    public AirQualityApiSyncException(String message) {
        super(message);
    }

    /**
     * Creates a new exception with the specified detail message and cause.
     *
     * @param message detail message describing the error
     * @param cause the underlying cause of the exception
     */
    public AirQualityApiSyncException(String message, Throwable cause) {
        super(message, cause);
    }
}
