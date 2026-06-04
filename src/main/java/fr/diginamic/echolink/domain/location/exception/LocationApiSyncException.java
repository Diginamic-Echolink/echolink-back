package fr.diginamic.echolink.domain.location.exception;

public class LocationApiSyncException extends RuntimeException {

    public LocationApiSyncException(String message) {
        super(message);
    }

    public LocationApiSyncException(String message, Throwable cause) {
        super(message, cause);
    }
}
