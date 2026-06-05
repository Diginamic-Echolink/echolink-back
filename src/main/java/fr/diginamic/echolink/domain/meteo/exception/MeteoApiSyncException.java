package fr.diginamic.echolink.domain.meteo.exception;

/**
 * Exception thrown when an error occurs while synchronizing weather data
 * from an external weather API.
 */
public class MeteoApiSyncException extends RuntimeException {

    /**
     * Creates a new weather API synchronization exception with the specified message.
     *
     * @param message detail message describing the error
     */
    public MeteoApiSyncException(String message) {
        super(message);
    }

    /**
     * Creates a new weather API synchronization exception with the specified
     * message and cause.
     *
     * @param message detail message describing the error
     * @param cause underlying cause of the error
     */
    public MeteoApiSyncException(String message, Throwable cause) {
        super(message, cause);
    }
}
