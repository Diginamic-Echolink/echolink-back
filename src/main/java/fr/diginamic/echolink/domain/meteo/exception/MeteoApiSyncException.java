package fr.diginamic.echolink.domain.meteo.exception;

public class MeteoApiSyncException extends RuntimeException {

    public MeteoApiSyncException(String message) {
        super(message);
    }

    public MeteoApiSyncException(String message, Throwable cause) {
        super(message, cause);
    }
}
