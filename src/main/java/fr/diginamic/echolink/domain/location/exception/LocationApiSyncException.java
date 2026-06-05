package fr.diginamic.echolink.domain.location.exception;

/**
 * Exception thrown when an error occurs while synchronizing location data
 * from an external data source.
 */
public class LocationApiSyncException extends RuntimeException {

    /**
     * Creates a new location synchronization exception with the specified message.
     *
     * @param message detail message describing the error
     */
    public LocationApiSyncException(String message) {
        super(message);
    }

    /**
     * Creates a new location synchronization exception with the specified
     * message and cause.
     *
     * @param message detail message describing the error
     * @param cause underlying cause of the error
     */
    public LocationApiSyncException(String message, Throwable cause) {
        super(message, cause);
    }
}
